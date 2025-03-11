package com.swifta.ussd.serviceClient;

import com.swifta.ussd.dto.EventData;
import com.swifta.ussd.dto.EventTypeData;
import com.swifta.ussd.dto.TicketBouquetData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

@Service
public class UssdProductServiceImpl implements UssdProductService{
    private final String coreBaseUrl;
    private final RestOperations restOperations;

    public UssdProductServiceImpl( @Value("${core.base.url}") String coreBaseUrl,RestOperations restOperations ) {
        this.coreBaseUrl = coreBaseUrl;
        this.restOperations = restOperations;

    }

    @Override
    public boolean validateTicket(String phoneNumber, String numberOfTicket) {
        String url = coreBaseUrl.concat("/validate-ticket");

        return false;
    }

    @Override
    public void processRefund(String phoneNumber, String numberOfTicket) {
        String url = coreBaseUrl.concat("/refund-ticket");


    }

    @Override
    public List<EventTypeData> getAllEventTypes() {
        String url = coreBaseUrl.concat("/event-types");

        return null;
    }

    @Override
    public List<EventData> getEventsByType(String eventType) {
        String url = coreBaseUrl.concat("/events/" + eventType + "?type");

        return null;
    }

    @Override
    public List<TicketBouquetData> getTicketBouquetsByEventId(String eventId) {
        String url = coreBaseUrl.concat("/tickets/" + eventId + "?id");

        return null;
    }
}
