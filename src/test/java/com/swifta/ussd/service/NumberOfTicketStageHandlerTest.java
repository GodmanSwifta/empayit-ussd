package com.swifta.ussd.service;

import com.swifta.ussd.constant.PropertyKeys;
import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.MovieTicketStageHandler;
import com.swifta.ussd.service.screens.NumberOfTicketStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.KYC_CONFIRMATION;
import static com.swifta.ussd.constant.Stage.TICKET_CONFIRMATION;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NumberOfTicketStageHandlerTest {

    private NumberOfTicketStageHandler numberOfTicketStageHandler;
    @Before
    public void setUp() throws Exception {
        numberOfTicketStageHandler = new NumberOfTicketStageHandler();
    }

    @Test
    public void getStage() {
        String stage = numberOfTicketStageHandler.getStage();
        assertEquals(Stage.NUMBER_OF_TICKET, stage);
    }

    @Test
    public void processStage() {
        String expected = TICKET_CONFIRMATION;

        UssdSession session = MockGenerator.generateSession("id");

        numberOfTicketStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PropertyKeys.NUMBER_OF_TICKET_RETRY, "false");
        String expected = "Enter number of tickets";

        USSDResponse ussdResponse = numberOfTicketStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }

    @Test
    public void loadPageRetry() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PropertyKeys.NUMBER_OF_TICKET_RETRY, "true");
        String expected = "Please enter a valid number of tickets";

        USSDResponse ussdResponse = numberOfTicketStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
