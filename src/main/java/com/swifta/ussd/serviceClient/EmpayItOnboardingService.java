package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.Id;
import com.swifta.ussd.dto.simreg.SimRegInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
public interface EmpayItOnboardingService {
    CustomerData validateCustomer(String dob);
    Id createCustomer(String Phone);

}
