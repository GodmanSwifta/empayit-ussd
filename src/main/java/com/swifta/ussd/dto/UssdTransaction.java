package com.swifta.ussd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class UssdTransaction {

    private String transactionId;
    private String reference;
    private String paymentReference;

    private BigDecimal fee;
    private BigDecimal amount;

}
