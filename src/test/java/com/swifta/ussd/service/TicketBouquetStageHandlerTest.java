package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.enums.BuyerType;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.TicketBouquetStageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.PropertyKeys.FLOW;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketBouquetStageHandlerTest {

    @Mock
    private UssdProductService productService;
    private TicketBouquetStageHandler ticketBouquetStageHandler;
    @Before
    public void setUp() throws Exception {
        ticketBouquetStageHandler = new TicketBouquetStageHandler(productService);
    }

    @Test
    public void processStageForPhone() {
        String expected = PHONE;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");
        session.setData(PURCHASE_OPTION_TYPE, BuyerType.AGENT.name());
        session.setData(FLOW, "not_resend_ticket");

        ticketBouquetStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStageForNumberOfTicket() {
        String expected = NUMBER_OF_TICKET;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");
        session.setData(PURCHASE_OPTION_TYPE, BuyerType.CUSTOMER.name());
        session.setData(FLOW, "not_resend_ticket");

        ticketBouquetStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = ticketBouquetStageHandler.getStage();
        assertEquals(Stage.TICKET_BOUQUET, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Ticket Bouquet\n1. VIP\n2. Executive\n3. Regular";

        USSDResponse ussdResponse = ticketBouquetStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
