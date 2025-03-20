package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swifta.ussd.enums.BuyerType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransactionRequest {
    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("buyer_id")
    private String buyerId;

    @JsonProperty("ticketCount")
    private int ticketCount;

    @JsonProperty("type")
    private BuyerType type;

    @JsonProperty("bouquet_id")
    private String bouquetId;

    @JsonProperty("agentCustomerMsisdn")
    private String agentCustomerMsisdn;


}
