package com.zonar.apivehiculos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ManufacturerDTO {
    @JsonProperty("Mfr_ID")
    private int mfrId;

    @JsonProperty("Mfr_Name")
    private String mfrName;

    @JsonProperty("Country")
    private String Country;

    @JsonProperty("Mfr_CommonName")
    private String mfrCommonName;

    @JsonProperty("VehicleTypes")
    private List<VehicleTypeDTO> vehicleTypes;
}
