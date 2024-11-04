package com.swifta.ussd.service;

import com.swifta.ussd.constant.PropertyKeys;
import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.NumberOfTicketStageHandler;
import com.swifta.ussd.service.screens.PaymentConfirmationStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PaymentConfirmationStageHandlerTest {

    private PaymentConfirmationStageHandler paymentConfirmationStageHandler;
    @Before
    public void setUp() throws Exception {
        paymentConfirmationStageHandler = new PaymentConfirmationStageHandler();
    }

    @Test
    public void getStage() {
        String stage = paymentConfirmationStageHandler.getStage();
        assertEquals(Stage.PAYMENT_CONFIRMATION, stage);
    }

    @Test
    public void processStage() {
        String expected = SMS_TICKET_CONFIRMATION;

        UssdSession session = MockGenerator.generateSession("id");

        paymentConfirmationStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Payment Confirmation\n\n--------------------";

        USSDResponse ussdResponse = paymentConfirmationStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }


}
