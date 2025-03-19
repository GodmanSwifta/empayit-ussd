package com.swifta.ussd.enums;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public enum PaymentStatus {
    SUCCESSFUL, FAILED, PENDING, ABANDONED, REFUNDED

}
