package com.swifta.ussd.serviceClient;

import com.swifta.ussd.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
    private Page<UssdTransaction> transactions;
    private final String coreBaseUrl;
    private final RestOperations restOperations;

    public UssdProductServiceImpl(@Value("${core.empayit.url}") String coreBaseUrl, RestOperations restOperations) {
        this.coreBaseUrl = coreBaseUrl;
        this.restOperations = restOperations;

    }

    @Override
    public boolean validateTicket(String phoneNumber, String numberOfTicket) {
        String url = coreBaseUrl.concat("/validate-ticket");
        HttpEntity request = new HttpEntity<>(null, getHeaders(numberOfTicket));
        ResponseEntity<Boolean> responseEntity;
        try {
            responseEntity = restOperations.exchange(url, HttpMethod.POST, request,
                    new ParameterizedTypeReference<Boolean>() {
                    });
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return false;
            }
            throw new RuntimeException(ex.getMessage());
        }
        return Objects.requireNonNull(responseEntity.getBody());
    }


    @Override
    public void processRefund(String phoneNumber, String numberOfTicket) {
        String url = coreBaseUrl.concat("/refund-ticket");
        HttpEntity request = new HttpEntity<>(null, getHeaders(numberOfTicket));
        ResponseEntity<Void> responseEntity;
        try {
            responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<Void>() {
                    });

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return;
            }
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Override
    public List<EventTypeData> getAllEventTypes() {
        String url = coreBaseUrl.concat("/event-types");
        HttpEntity request = new HttpEntity(null, getHeaders(""));
        ResponseEntity<List<EventTypeData>> responseEntity;
            try {
                responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<List<EventTypeData>>() {
                        });
                return responseEntity.getBody();
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return null;
                }
                throw new RuntimeException(ex.getMessage());
            }
    }

    @Override
    public List<EventData> getEventsByType(String eventType) {
        String url = coreBaseUrl.concat("/events/" + eventType + "?type");
        HttpEntity request = new HttpEntity<>(null, getHeaders(eventType));
        ResponseEntity<List<EventData>> responseEntity;
            try {
                responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<List<EventData>>() {
                        });
                return responseEntity.getBody();
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return null;
                }
                throw new RuntimeException(ex.getMessage());
            }
    }

    @Override
    public List<TicketBouquetData> getTicketBouquetsByEventId(String eventId) {
        String url = coreBaseUrl.concat("/tickets/" + eventId + "?id");
        HttpEntity request = new HttpEntity<>(null, getHeaders(eventId));
        ResponseEntity<List<TicketBouquetData>> responseEntity;
            try {
                responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<List<TicketBouquetData>>() {
                        });
                return responseEntity.getBody();
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return null;
                }
                throw new RuntimeException(ex.getMessage());
            }
    }

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest transactionRequest) {
        String url = coreBaseUrl.replace("/empayit", "").concat("/transactions/create");
        HttpEntity<CreateTransactionRequest> httpEntity = new HttpEntity<>(transactionRequest, getHeaders(""));
        ResponseEntity<AppSuccessResponse<CreateTransactionResponse>> responseEntity;
        try {
            responseEntity = restOperations.exchange(
                    url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return responseEntity.getBody().getData();
    }

    @Override
    public InitiatePaymentResponse initiatePayment(InitiatePaymentRequest paymentRequest) {
        String url = coreBaseUrl.replace("/empayit", "").concat("/transactions/pay");
        HttpEntity<InitiatePaymentRequest> httpEntity = new HttpEntity<>(paymentRequest, getHeaders(""));
        ResponseEntity<InitiatePaymentResponse> responseEntity;
        try {
            responseEntity = restOperations.exchange(
                    url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return responseEntity.getBody();
    }

    @Override
    public Page<UssdTransaction> listTransactions(String merchantId,
                                                  UssdTransactionFilter filterRequest,
                                                  int page,
                                                  int pageSize) {
        String url = coreBaseUrl
                .concat("/transaction?merchantId")
                .concat(page + "&page=" + pageSize + "&page size=")
                .concat(merchantId);
        HttpEntity<UssdTransactionFilter> request =
                new HttpEntity<>(filterRequest, getHeaders(merchantId));
        ResponseEntity<Page<UssdTransaction>> responseEntity;
            try {
                responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<Page<UssdTransaction>>() {
                        });
                return responseEntity.getBody();
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return null;
                }
                throw new RuntimeException(ex.getMessage());
            }
    }

    @Override
    public UssdTransaction getTransaction(String merchantId, String transactionId) {
        String url = coreBaseUrl.concat("transaction?merchantId=" + merchantId);
        HttpEntity request = new HttpEntity<>(null, getHeaders(transactionId));
        ResponseEntity<UssdTransaction> responseEntity;
        try {
            responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<UssdTransaction>() {
                    });
            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return null;
            }
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public UssdTransaction getTransactionByReference(String reference) {
        String url = coreBaseUrl.concat("/by-reference/" + reference + "reference");
        HttpEntity<String> request = new HttpEntity<>(null, getHeaders(reference));
        ResponseEntity<UssdTransaction> responseEntity;
        try {
            responseEntity = restOperations.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<UssdTransaction>() {
                    });
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return null;
            }
            throw new RuntimeException(ex.getMessage());
        }
        return responseEntity.getBody();
    }

    private HttpHeaders getHeaders(String s) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}


