package com.zonar.apivehiculos.repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonar.apivehiculos.dto.ManufacturerDTO;
import com.zonar.apivehiculos.dto.ManufacturerResponseDTO;
import com.zonar.apivehiculos.dto.VehicleTypeDTO;
import com.zonar.apivehiculos.model.Manufacturer;
import com.zonar.apivehiculos.model.VehicleType;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ManufacturerService {
    private static final Logger logger = LoggerFactory.getLogger(ManufacturerService.class);

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @PostConstruct
    @Transactional
    public void init() {
        fetchData();
    }

    public void fetchData() {
        try {
            vehicleTypeRepository.deleteAll();
            manufacturerRepository.deleteAll();

            WebClient webClient = WebClient.create("https://vpic.nhtsa.dot.gov/api/vehicles");
            Mono<ManufacturerResponseDTO> responseMono = webClient.get()
                    .uri("/getallmanufacturers?format=json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(System.out::println)
                    .map(responseBody -> {
                        try {
                            return new ObjectMapper().readValue(responseBody, ManufacturerResponseDTO.class);
                        } catch (JsonProcessingException e) {
                            logger.error("Error processing JSON response", e);
                            throw new RuntimeException(e);
                        }
                    });
            //.bodyToMono(ManufacturerResponseDTO.class);

            responseMono.subscribe(responseDTO -> {
                for (ManufacturerDTO manufacturerDTO : responseDTO.getResults()) {
                    Manufacturer manufacturer = new Manufacturer();
                    manufacturer.setMfrId(manufacturerDTO.getMfrId());
                    manufacturer.setMfrName(manufacturerDTO.getMfrName());
                    manufacturer.setCountry(manufacturerDTO.getCountry());
                    manufacturer.setMfrCommonName(manufacturerDTO.getMfrCommonName());
                    Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);

                    for (VehicleTypeDTO vehicleTypeDTO : manufacturerDTO.getVehicleTypes()) {
                        VehicleType vehicleType = new VehicleType();
                        vehicleType.setIsPrimary(vehicleTypeDTO.isPrimary());
                        vehicleType.setName(vehicleTypeDTO.getName());
                        vehicleType.setManufacturer(savedManufacturer);
                        vehicleTypeRepository.save(vehicleType);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("An error occurred while fetching and saving data", e);
        }
    }

    @Cacheable("allManufacturers")
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }
    @Cacheable("manufacturer")
    public Manufacturer getManufacturerById(Long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "manufacturersByCountry", key = "#country")
    public List<Manufacturer> getManufacturersByCountry(String country) {
        return manufacturerRepository.findByCountry(country);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }
}