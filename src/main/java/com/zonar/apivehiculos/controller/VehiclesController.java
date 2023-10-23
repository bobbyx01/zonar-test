package com.zonar.apivehiculos.controller;

import com.zonar.apivehiculos.model.Manufacturer;
import com.zonar.apivehiculos.model.ManufacturerListWrapper;
import com.zonar.apivehiculos.repository.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api/vehicles", produces = { "application/json", "application/xml" })
public class VehiclesController {

    private static final Logger logger = LoggerFactory.getLogger(VehiclesController.class);

    @Autowired
    private ManufacturerService manufacturerService;

    // Endpoint to get all manufacturers
    @GetMapping
    public ManufacturerListWrapper getAllManufacturers() {
        logger.info("Received request to get all manufacturers");

        ManufacturerListWrapper wrapper = new ManufacturerListWrapper();
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        if (manufacturers == null || manufacturers.isEmpty()) {
            logger.warn("No manufacturers found");
            manufacturers = Collections.emptyList();
        }
        wrapper.setManufacturers(manufacturers);

        logger.info("Returning {} manufacturers", manufacturers.size());
        return wrapper;
    }

    // Endpoint to get manufacturers by country
    @GetMapping("/country/{country}")
    public ManufacturerListWrapper getManufacturersByCountry(@PathVariable String country) {
        logger.info("Received request to get manufacturers by country: {}", country);

        ManufacturerListWrapper wrapper = new ManufacturerListWrapper();
        List<Manufacturer> manufacturers = manufacturerService.getManufacturersByCountry(country);
        if (manufacturers == null || manufacturers.isEmpty()) {
            logger.warn("No manufacturers found for country: {}", country);
            manufacturers = Collections.emptyList();
        }
        wrapper.setManufacturers(manufacturers);

        logger.info("Returning {} manufacturers for country: {}", manufacturers.size(), country);
        return wrapper;
    }
}