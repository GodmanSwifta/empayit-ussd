package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swifta.ussd.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UssdTransactionFilter {

    private String merchantId;
    private Boolean bulkBuy;
    private String customerName;
    private PaymentStatus paymentStatus;
    private List<String> transactionIds;



}
