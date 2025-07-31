package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.ResendTicketResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.util.List;

@Service
public class ResendTicketServiceImpl implements ResendTicketService {
    private final String coreBaseUrl;
    private final RestOperations restOperations;

    public ResendTicketServiceImpl(@Value("${core.empayit.url}") String coreBaseUrl,
                                   RestOperations restOperations) {
        this.coreBaseUrl = coreBaseUrl;
        this.restOperations = restOperations;
    }


    @Override
    public List<ResendTicketResponse> resendTicket(String phoneNumber) {
        String url = coreBaseUrl + "/confirm-resend/" + phoneNumber;
        HttpEntity<String> request = new HttpEntity<>(null, getHeaders(""));

        ResponseEntity<List<ResendTicketResponse>> responseEntity;

        try {
            responseEntity = restOperations.exchange(url, HttpMethod.POST, request,
                    new ParameterizedTypeReference<List<ResendTicketResponse>>() {
                    });

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return null;
            }
            throw new RuntimeException("Failed to resend ticket: " + ex.getMessage());

        }

        return responseEntity.getBody();

    }



    private HttpHeaders getHeaders(String s) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
