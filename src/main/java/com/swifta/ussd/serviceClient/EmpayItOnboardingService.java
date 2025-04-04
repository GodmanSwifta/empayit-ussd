package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.request.CreateCustomerRequest;
import org.springframework.stereotype.Service;


@Service
public interface EmpayItOnboardingService {
    CustomerData validateCustomer(String phoneNumber);
    CustomerData createCustomer(CreateCustomerRequest customerRequest);

}
