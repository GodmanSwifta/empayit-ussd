package com.swifta.ussd.serviceClient;

import com.swifta.ussd.sms.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Component
public class SmsApiClient {
    private final String smsUrl;
    private final String apiKeyName;
    private final String apikeyValue;
    private final String ussdProvider;
    private final RestTemplate restTemplate;

    public SmsApiClient(RestTemplate restTemplate,
                        @Value("${sms.url}") String smsUrl,
                        @Value("${sms.key.name}") String apiKeyName,
                        @Value("${ussd.provider}") String ussdProvider,
                        @Value("${sms.key.value}") String apikeyValue) {
        this.restTemplate = restTemplate;
        this.smsUrl = smsUrl;
        this.apiKeyName = apiKeyName;
        this.ussdProvider = ussdProvider;
        this.apikeyValue = apikeyValue;
    }


    public void sendSms(String msisdn, String message, String senderId, String shortcode) {

        if(ussdProvider.equals("airtel")) {
            return;//TODO: AIRTEL SMS IMPLEMENTATION
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(apiKeyName, apikeyValue);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SmsRequest> httpEntity = new HttpEntity<>(SmsRequest.builder()
                .clientCorrelatorId(UUID.randomUUID().toString())
                .message(message)
                .receiverAddress(Collections.singletonList(msisdn))
                .senderAddress(senderId)
                .serviceCode(shortcode)
                .requestDeliveryReceipt(false)
                .build(), headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(smsUrl, HttpMethod.POST,
                httpEntity, Void.class);
    }

}
