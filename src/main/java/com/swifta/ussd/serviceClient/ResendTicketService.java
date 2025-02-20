package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import org.springframework.stereotype.Service;

@Service
public interface ResendTicketService {

    CustomerData phoneNumberValidation(String PhoneNumber);
}
