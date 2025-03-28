package com.swifta.ussd.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventOptionData {
    @JsonProperty("option_name")
    private String optionName;
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_name")
    private String eventName;




    public EventOptionData(EventData eventData) {
        this.eventName = eventData.getEventName();
        this.eventId = eventData.getEventId();



    }
}
