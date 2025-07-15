package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.AgentData;
import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.request.CreateCustomerRequest;
import org.springframework.stereotype.Service;


@Service
public interface EmpayItOnboardingService {
    CustomerData validateCustomer(String phoneNumber);

    AgentData validateAgentByPhone(String phoneNumber);

    CustomerData createCustomer(CreateCustomerRequest customerRequest);

}
