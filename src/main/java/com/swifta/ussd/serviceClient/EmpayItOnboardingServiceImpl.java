package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.request.CreateCustomerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.time.LocalDate;
import java.util.Arrays;

import static io.swagger.v3.core.util.AnnotationsUtils.getHeaders;

@Slf4j
@Service
public class EmpayItOnboardingServiceImpl implements EmpayItOnboardingService{

    private final RestOperations restOperations;
    private final String simRegUrl;

    public EmpayItOnboardingServiceImpl(RestOperations restOperations, String simRegUrl) {
        this.restOperations = restOperations;
        this.simRegUrl = simRegUrl;
    }


    @Override
    public CustomerData validateCustomer(String phoneNumber) {
        String url = simRegUrl.concat("/user-info?phoneNumber="+ phoneNumber);
        HttpEntity<String> request = new HttpEntity<>(null,getHeaders(""));

        ResponseEntity<CustomerData> responseEntity;

        try{
            responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<CustomerData>() {});
        }catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return null;
            }
            throw new RuntimeException("API Error: " + ex.getMessage());
        }


        return responseEntity.getBody();
    }



    @Override
    public CustomerData CreateCustomer(CreateCustomerRequest customerRequest) {
        String url = simRegUrl.concat("/create-customer");
        HttpEntity<CreateCustomerRequest> httpEntity = new HttpEntity<>(customerRequest, getHeaders(""));

        ResponseEntity<CustomerData> responseEntity;


        try {
            responseEntity = restOperations.exchange(
                    url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {});

        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Failed to create customer: " + ex.getMessage());
        }


        return responseEntity.getBody();


    }







 private HttpHeaders getHeaders(String token) {
    HttpHeaders headers = new HttpHeaders();
    if (token != null && !token.isEmpty()) {
        headers.set("Authorization", "Bearer " + token);
    }
    headers.set("Content-Type", "application/json");
    return headers;
}
}
