package com.swifta.ussd.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@XmlRootElement(name = "request")
public class USSDRequest {
    private String msisdn;
    private String imsi;
    private int newRequest;
    private String sessionId;
    private String dateFormat;
    private String transactionId;
    private String subscriberInput;
    @XmlAttribute
    private String type;
    private FreeflowRequest freeflow;
}
