package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swifta.ussd.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiatePaymentResponse {
    private String transactionId;
    private PaymentStatus paymentStatus;

}
