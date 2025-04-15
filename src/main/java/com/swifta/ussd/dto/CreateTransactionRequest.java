package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swifta.ussd.enums.BuyerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransactionRequest {
    private String eventId;
    private String buyerId;
    private int ticketCount;
    private BuyerType type;
    private String bouquetId;
    private String agentCustomerMsisdn;


}
