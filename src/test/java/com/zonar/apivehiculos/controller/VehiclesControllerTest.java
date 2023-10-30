package com.zonar.apivehiculos.controller;

import com.zonar.apivehiculos.model.Manufacturer;
import com.zonar.apivehiculos.repository.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class VehiclesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ManufacturerService manufacturerService;

    @InjectMocks
    private VehiclesController vehiclesController;

    private Manufacturer manufacturer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vehiclesController).build();

        manufacturer = new Manufacturer();
    }


    @Test
    public void testGetAllManufacturersRespondsWithJson() throws Exception {
        List<Manufacturer> manufacturers = Arrays.asList(manufacturer);

        when(manufacturerService.getAllManufacturers()).thenReturn(manufacturers);

        mockMvc.perform(get("/api/vehicles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllManufacturersRespondsWithXml() throws Exception {
        List<Manufacturer> manufacturers = Arrays.asList(manufacturer);

        when(manufacturerService.getAllManufacturers()).thenReturn(manufacturers);

        mockMvc.perform(get("/api/vehicles")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML));
    }

    @Test
    public void testGetManufacturersByCountryRespondsWithJson() throws Exception {
        List<Manufacturer> manufacturers = Arrays.asList(manufacturer);

        when(manufacturerService.getManufacturersByCountry("Japan")).thenReturn(manufacturers);

        mockMvc.perform(get("/api/vehicles/country/Japan")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetManufacturersByCountryRespondsWithXml() throws Exception {
        List<Manufacturer> manufacturers = Arrays.asList(manufacturer);

        when(manufacturerService.getManufacturersByCountry("Japan")).thenReturn(manufacturers);

        mockMvc.perform(get("/api/vehicles/country/Japan")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML));
    }

    @Test
    public void testGetManufacturersByCountryInvalidCountry() throws Exception {
        when(manufacturerService.getManufacturersByCountry("InvalidCountry")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/vehicles/country/InvalidCountry")  // Corrected the endpoint here
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Changed to expect 200 status
                .andExpect(content().json("{\"manufacturers\":[]}"));  // Expecting empty list
    }
}
