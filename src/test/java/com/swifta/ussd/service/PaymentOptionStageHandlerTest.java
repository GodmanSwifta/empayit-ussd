package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.PaymentConfirmationStageHandler;
import com.swifta.ussd.service.screens.PaymentOptionStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.MOMO_PIN;
import static com.swifta.ussd.constant.Stage.SMS_TICKET_CONFIRMATION;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PaymentOptionStageHandlerTest {

    private PaymentOptionStageHandler paymentOptionStageHandler;
    @Before
    public void setUp() throws Exception {
        paymentOptionStageHandler = new PaymentOptionStageHandler();
    }

    @Test
    public void getStage() {
        String stage = paymentOptionStageHandler.getStage();
        assertEquals(Stage.PAYMENT_OPTION, stage);
    }

    @Test
    public void processStage() {
        String expected = MOMO_PIN;

        UssdSession session = MockGenerator.generateSession("id");

        paymentOptionStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select payment option\n\n1. MTN MoMo";

        USSDResponse ussdResponse = paymentOptionStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }


}
