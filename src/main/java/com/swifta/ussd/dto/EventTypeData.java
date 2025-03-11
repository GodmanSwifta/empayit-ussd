package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventTypeData {
    @JsonProperty("id")
    private String Id;
    @JsonProperty("event_name")
    private String eventName;

}
