package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.TicketResendConfirmationStageHandler;
import com.swifta.ussd.serviceClient.ResendTicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TicketResentConfirmationStageHandlerTest {

    private TicketResendConfirmationStageHandler ticketResendConfirmationStageHandler;
    private ResendTicketService resendTicketService;
    @Before
    public void setUp() throws Exception {
        resendTicketService = mock(ResendTicketService.class);
        ticketResendConfirmationStageHandler = new TicketResendConfirmationStageHandler(resendTicketService);
    }

    @Test
    public void processStage() {
        String expected = TICKET_SENT;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        ticketResendConfirmationStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = TICKET_RESEND_CONFIRMATION;
        assertEquals(stage, ticketResendConfirmationStageHandler.getStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Please confirm you want to resend to 08090989098\n1. Yes\n2. No";

        USSDResponse ussdResponse = ticketResendConfirmationStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
