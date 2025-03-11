package com.swifta.ussd.serviceClient;

import com.swifta.ussd.dto.EventData;
import com.swifta.ussd.dto.EventTypeData;
import com.swifta.ussd.dto.TicketBouquetData;

import java.util.List;

public interface UssdProductService {
    boolean validateTicket(String phoneNumber, String numberOfTicket);
    void processRefund(String phoneNumber, String numberOfTicket);

    List<EventTypeData> getAllEventTypes();

    List<EventData> getEventsByType(String eventType);
    List<TicketBouquetData> getTicketBouquetsByEventId(String eventId);
}
