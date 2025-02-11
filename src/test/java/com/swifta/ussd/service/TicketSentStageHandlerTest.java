package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.TicketResendConfirmationStageHandler;
import com.swifta.ussd.service.screens.TicketSentStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.TICKET_RESEND_CONFIRMATION;
import static com.swifta.ussd.constant.Stage.TICKET_SENT;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketSentStageHandlerTest {

    private TicketSentStageHandler ticketSentStageHandler;
    @Before
    public void setUp() throws Exception {
        ticketSentStageHandler = new TicketSentStageHandler();
    }

    @Test
    public void getStage() {
        String stage = TICKET_SENT;
        assertEquals(stage, ticketSentStageHandler.getStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Dear Customer,\nYour ticket details has been sent. Thank you for your interest in EmpayIT.";

        USSDResponse ussdResponse = ticketSentStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
