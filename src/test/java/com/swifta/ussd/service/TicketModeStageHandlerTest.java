package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.TicketConfirmationStageHandler;
import com.swifta.ussd.service.screens.TicketModeStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.PropertyKeys.FLOW;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketModeStageHandlerTest {

    private TicketModeStageHandler ticketModeStageHandler;
    @Before
    public void setUp() throws Exception {
        ticketModeStageHandler = new TicketModeStageHandler();
    }

    @Test
    public void processStageForPhone() {
        String expected = PHONE;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");
        session.setData(PURCHASE_OPTION_TYPE, "agent");
        session.setData(FLOW, "not_resend_ticket");

        ticketModeStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStageForNumberOfTicket() {
        String expected = NUMBER_OF_TICKET;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");
        session.setData(PURCHASE_OPTION_TYPE, "self");
        session.setData(FLOW, "not_resend_ticket");

        ticketModeStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = ticketModeStageHandler.getStage();
        assertEquals(Stage.TICKET_MODE, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Ticket Bouquet\n1. VIP\n2. Executive\n3. Regular";

        USSDResponse ussdResponse = ticketModeStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
