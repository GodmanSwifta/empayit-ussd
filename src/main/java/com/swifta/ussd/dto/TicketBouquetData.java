package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketBouquetData {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("event")
    private String event;
}
