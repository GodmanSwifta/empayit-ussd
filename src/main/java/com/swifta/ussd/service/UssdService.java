package com.swifta.ussd.service;


import com.swifta.ussd.dto.USSDRequest;
import com.swifta.ussd.dto.USSDResponse;

public interface UssdService {
    USSDResponse processRequest(USSDRequest request);
}
