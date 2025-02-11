package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.SmsTicketConfirmationStageHandler;
import com.swifta.ussd.service.screens.TAndCDeclineStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TAndCDeclineStageHandlerTest {

    private TAndCDeclineStageHandler tAndCDeclineStageHandler;
    @Before
    public void setUp() throws Exception {
        tAndCDeclineStageHandler = new TAndCDeclineStageHandler();
    }

    @Test
    public void getStage() {
        String stage = tAndCDeclineStageHandler.getStage();
        assertEquals(Stage.T_AND_C_DECLINE, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Dear Customer\nYou need to accept T&C to use this service\nThank you!";

        USSDResponse ussdResponse = tAndCDeclineStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
