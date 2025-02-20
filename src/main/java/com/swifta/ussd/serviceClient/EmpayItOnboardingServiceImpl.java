package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.Dob;
import com.swifta.ussd.dto.Id;
import com.swifta.ussd.dto.simreg.SimRegInfo;
import com.swifta.ussd.service.simreg.SimRegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.time.LocalDate;
import java.util.Arrays;

import static io.swagger.v3.core.util.AnnotationsUtils.getHeaders;

@Slf4j
@Service
public class EmpayItOnboardingServiceImpl implements EmpayItOnboardingService{

    private final RestOperations restOperations;

    public EmpayItOnboardingServiceImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }


    @Override
    public CustomerData validateCustomer(String dob) {
        Dob dob1 = new Dob();
        dob1.setDateOfBirth(LocalDate.parse(dob));
       return null;
    }

    @Override
    public Id createCustomer(String Phone) {
        return null;
    }


//
//    private HttpHeaders getHeaders(String token) {
//        log.info("THE USER TOKEN IS => {}", token);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        httpHeaders.set("token", token);
//        return httpHeaders;
//    }
}
