package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventData {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("merchant_name")
    private String merchantName;

}
