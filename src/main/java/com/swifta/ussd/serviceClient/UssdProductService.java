package com.swifta.ussd.serviceClient;

public interface UssdProductService {
    boolean validateTicket(String phoneNumber, String numberOfTicket);

    Void processRefund(String phoneNumber, String numberOfTicket);

    Void ValidateAgentTicketInfo(String numberOfTickets);
    Void ticketDetail( String numberOfTicket);
    Void validatePin(String momoPin);
}
