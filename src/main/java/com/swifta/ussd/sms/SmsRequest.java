package com.swifta.ussd.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
    private List<String> receiverAddress;
    private String clientCorrelatorId;
    private String senderAddress;
    private String serviceCode;
    private boolean requestDeliveryReceipt;
    private String message;
}