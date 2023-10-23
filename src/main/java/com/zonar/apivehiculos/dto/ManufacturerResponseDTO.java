package com.zonar.apivehiculos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ManufacturerResponseDTO {
    @JsonProperty("Count")
    private int Count;
    @JsonProperty("Message")
    private String Message;
    @JsonProperty("SearchCriteria")
    private String SearchCriteria;
    @JsonProperty("Results")
    private List<ManufacturerDTO> Results;
}

