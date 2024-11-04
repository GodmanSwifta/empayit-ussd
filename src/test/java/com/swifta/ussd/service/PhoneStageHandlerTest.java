package com.swifta.ussd.service;

import com.swifta.ussd.constant.PropertyKeys;
import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.NumberOfTicketStageHandler;
import com.swifta.ussd.service.screens.PhoneStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.NUMBER_OF_TICKET;
import static com.swifta.ussd.constant.Stage.TICKET_CONFIRMATION;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PhoneStageHandlerTest {

    private PhoneStageHandler phoneStageHandler;
    @Before
    public void setUp() throws Exception {
        phoneStageHandler = new PhoneStageHandler();
    }

    @Test
    public void getStage() {
        String stage = phoneStageHandler.getStage();
        assertEquals(Stage.PHONE, stage);
    }

    @Test
    public void processStage() {
        String expected = NUMBER_OF_TICKET;

        UssdSession session = MockGenerator.generateSession("id");

        phoneStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PropertyKeys.PHONE_RETRY, "false");
        String expected = "Please enter Phone number";

        USSDResponse ussdResponse = phoneStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }

    @Test
    public void loadPageRetry() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PropertyKeys.PHONE_RETRY, "true");
        String expected = "Dear Customer\n\nPlease enter a valid Phone number";

        USSDResponse ussdResponse = phoneStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
