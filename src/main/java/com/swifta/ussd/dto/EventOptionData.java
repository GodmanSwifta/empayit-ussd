package com.swifta.ussd.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventOptionData {
    private String eventId;
    private String eventName;
    private String merchantName;
    private String eventDate;


    public EventOptionData(EventData eventData) {
        this.eventName = eventData.getEventName();
        this.eventId = eventData.getEventId();
        this.merchantName = eventData.getMerchantName();
        this.eventDate = eventData.getEventDate();
    }
}
