package com.swifta.ussd.serviceClient;

public interface UssdProductService {
    boolean validateTicket(String phoneNumber, String numberOfTicket);

    Void processRefund(String phoneNumber, String numberOfTicket);



}
