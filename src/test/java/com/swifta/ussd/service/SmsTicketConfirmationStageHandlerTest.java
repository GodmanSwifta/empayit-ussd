package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.SmsTicketConfirmationStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SmsTicketConfirmationStageHandlerTest {

    private SmsTicketConfirmationStageHandler smsTicketConfirmationStageHandler;
    @Before
    public void setUp() throws Exception {
        smsTicketConfirmationStageHandler = new SmsTicketConfirmationStageHandler();
    }

    @Test
    public void getStage() {
        String stage = smsTicketConfirmationStageHandler.getStage();
        assertEquals(Stage.SMS_TICKET_CONFIRMATION, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "SMS Ticket Confirmation";

        USSDResponse ussdResponse = smsTicketConfirmationStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
