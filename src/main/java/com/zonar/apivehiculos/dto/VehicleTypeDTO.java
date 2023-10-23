package com.zonar.apivehiculos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VehicleTypeDTO {
    @JsonProperty("IsPrimary")
    private boolean isPrimary;

    @JsonProperty("Name")
    private String Name;
}
