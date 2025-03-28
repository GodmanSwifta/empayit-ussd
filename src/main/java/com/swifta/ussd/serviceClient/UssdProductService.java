package com.swifta.ussd.serviceClient;

import com.swifta.ussd.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UssdProductService {
    boolean validateTicket(String phoneNumber, String numberOfTicket);
    void processRefund(String phoneNumber, String numberOfTicket);

    List<EventTypeData> getAllEventTypes();

    List<EventData> getEventsByType(String eventType);
    List<TicketBouquetData> getTicketBouquetsByEventId(String eventId);


    CreateTransactionResponse createTransaction(CreateTransactionRequest request);

    InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request);

    Page<UssdTransaction> listTransactions(String merchantId, UssdTransactionFilter filterRequest, int page, int pageSize);

    UssdTransaction getTransaction(String merchantId, String transactionId);

    UssdTransaction getTransactionByReference(String reference);
}
