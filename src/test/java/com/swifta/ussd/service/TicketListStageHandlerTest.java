package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.TicketListStageHandler;
import com.swifta.ussd.service.screens.TicketResendConfirmationStageHandler;
import com.swifta.ussd.serviceClient.ResendTicketService;
import com.swifta.ussd.serviceClient.SmsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TicketListStageHandlerTest {

    private TicketListStageHandler ticketListStageHandler;
    private ResendTicketService resendTicketService;
    private SmsService smsService;
    @Before
    public void setUp() throws Exception {
        resendTicketService = mock(ResendTicketService.class);
        ticketListStageHandler = new TicketListStageHandler(resendTicketService, smsService);
    }

    @Test
    public void processStage() {
        String expected = TICKET_RESEND_CONFIRMATION;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        ticketListStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = TICKET_LIST;
        assertEquals(stage, ticketListStageHandler.getStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Event Ticket ID\n1. Ticket ID/9897302/Oloture Dro";

        USSDResponse ussdResponse = ticketListStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
