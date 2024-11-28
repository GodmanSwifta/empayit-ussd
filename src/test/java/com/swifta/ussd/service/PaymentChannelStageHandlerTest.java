package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.PaymentChannelStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.PropertyKeys.PAYMENT_METHOD;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PaymentChannelStageHandlerTest {

    private PaymentChannelStageHandler paymentChannelStageHandler;
    @Before
    public void setUp() throws Exception {
        paymentChannelStageHandler = new PaymentChannelStageHandler();
    }

    @Test
    public void getStage() {
        String stage = paymentChannelStageHandler.getStage();
        assertEquals(Stage.PAYMENT_CHANNEL, stage);
    }

    @Test
    public void loadPageMomoPin() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PURCHASE_OPTION_TYPE, "agent");
        session.setData(PAYMENT_METHOD, "mtn_momo");
        String expected = "Enter MoMo Pin";

        USSDResponse ussdResponse = paymentChannelStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }

    @Test
    public void loadPagePromocode() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PURCHASE_OPTION_TYPE, "self");
        session.setData(PAYMENT_METHOD, "promocode");
        String expected = "Enter Event promocode";

        USSDResponse ussdResponse = paymentChannelStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
