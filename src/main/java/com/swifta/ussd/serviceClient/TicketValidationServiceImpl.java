package com.swifta.ussd.serviceClient;

public interface TicketValidationServiceImpl {
    boolean validateTicket(String phoneNumber, String numberOfTicket);

    
}
