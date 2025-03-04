package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.request.CreateCustomerRequest;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;



@Slf4j
@Service
public class EmpayItOnboardingServiceImpl implements EmpayItOnboardingService{

    private final RestOperations restOperations;
    private final String coreBaseUrl;


    public EmpayItOnboardingServiceImpl( RestOperations restOperations, @Value("${core.base.url}") String coreBaseUrl) {
        this.restOperations = restOperations;
        this.coreBaseUrl = coreBaseUrl;

    }


    @Override
    public CustomerData validateCustomer(String phoneNumber) {
        String url = coreBaseUrl.concat("/customer-validate");
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
        String url = coreBaseUrl.concat("/create-customer");
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







    private HttpHeaders getHeaders(String s) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
