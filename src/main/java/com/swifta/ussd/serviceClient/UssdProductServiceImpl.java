package com.swifta.ussd.serviceClient;

import com.swifta.ussd.dto.EventData;
import com.swifta.ussd.dto.EventTypeData;
import com.swifta.ussd.dto.TicketBouquetData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UssdProductServiceImpl implements UssdProductService {

    private List<TicketBouquetData> ticketBouquet = new ArrayList<>();
    private List<EventData> event = new ArrayList<>();
    private List<EventTypeData> eventType = new ArrayList<>();
    private final String coreBaseUrl;
    private final RestOperations restOperations;

    public UssdProductServiceImpl(@Value("${core.base.url}") String coreBaseUrl, RestOperations restOperations) {
        this.coreBaseUrl = coreBaseUrl;
        this.restOperations = restOperations;

    }

    @Override
    public boolean validateTicket(String phoneNumber, String numberOfTicket) {
        String url = coreBaseUrl.concat("/validate-ticket");
        HttpEntity request = new HttpEntity<>(null,getHeaders(numberOfTicket));
        ResponseEntity<Boolean> responseEntity;
        try {
            responseEntity = restOperations.exchange(url, HttpMethod.POST, request,
                    new ParameterizedTypeReference<Boolean>() {});
        }catch (HttpClientErrorException ex){
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                return false;
            }
            throw new RuntimeException(ex.getMessage());
        }
        return Objects.requireNonNull(responseEntity.getBody());
        }


    @Override
    public void processRefund(String phoneNumber, String numberOfTicket) {
        String url = coreBaseUrl.concat("/refund-ticket");
        HttpEntity request = new HttpEntity<>(null,getHeaders(numberOfTicket));
        ResponseEntity<Void> responseEntity;
        try {
            responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<Void>() {});

        }catch (HttpClientErrorException ex){
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                return;
            }
            throw new RuntimeException( ex.getMessage());
        }

        return ;
    }


    @Override
    public List<EventTypeData> getAllEventTypes() {
        String url = coreBaseUrl.concat("/event-types");
        HttpEntity request = new HttpEntity( null, getHeaders(""));
       ResponseEntity<List<EventTypeData>> responseEntity;
       if (eventType.isEmpty()){
           try {
               responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                       new ParameterizedTypeReference<List<EventTypeData>>() {});
                  eventType = responseEntity.getBody();
           }catch (HttpClientErrorException ex){
               if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                   return null;
               }
               throw new RuntimeException(ex.getMessage());
           }
       }
        return eventType;
    }

    @Override
    public List<EventData> getEventsByType(String eventType) {
        String url = coreBaseUrl.concat("/events/" + eventType + "?type");
        HttpEntity request = new HttpEntity<>(null, getHeaders(eventType));
        ResponseEntity<List<EventData>> responseEntity;
        if (event.isEmpty()) {
            try {
                responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<List<EventData>>() {
                });
                event = responseEntity.getBody();
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return null;
                }
                throw new RuntimeException(ex.getMessage());
            }
        }
        return event;
    }

    @Override
    public List<TicketBouquetData> getTicketBouquetsByEventId(String eventId) {
        String url = coreBaseUrl.concat("/tickets/" + eventId + "?id");
        HttpEntity request = new  HttpEntity<>(null, getHeaders(eventId));
        ResponseEntity<List<TicketBouquetData>> responseEntity;
        if (ticketBouquet.isEmpty()) {
            try {
                responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<List<TicketBouquetData>>() {
                });
                ticketBouquet = responseEntity.getBody();
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return null;
                }
                throw new RuntimeException(ex.getMessage());
            }
        }
        return ticketBouquet;
    }

    private HttpHeaders getHeaders(String s) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}


