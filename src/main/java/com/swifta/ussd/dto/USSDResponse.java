package com.swifta.ussd.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "response")
@XmlRootElement(name = "response")
public class USSDResponse {
    private String msisdn;
    private String appDrivenMenuCode;
    private String applicationResponse;
    private Freeflow freeflow;
}
