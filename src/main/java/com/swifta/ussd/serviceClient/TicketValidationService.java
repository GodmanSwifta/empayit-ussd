package com.swifta.ussd.serviceClient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class TicketValidationService implements TicketValidationServiceImpl{

    private final RestOperations restOperations;

    public TicketValidationService( RestOperations restOperations) {
        this.restOperations = restOperations;
    }
    @Override
    public boolean validateTicket(String phoneNumber, String numberOfTicket) {
        return false;
    }

}
