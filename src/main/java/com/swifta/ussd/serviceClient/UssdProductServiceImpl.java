package com.swifta.ussd.serviceClient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class UssdProductServiceImpl implements UssdProductService{

    private final RestOperations restOperations;
    public UssdProductServiceImpl( RestOperations restOperations) {
        this.restOperations = restOperations;
    }
    @Override
    public boolean validateTicket(String phoneNumber, String numberOfTicket) {
        return false;
    }

}
