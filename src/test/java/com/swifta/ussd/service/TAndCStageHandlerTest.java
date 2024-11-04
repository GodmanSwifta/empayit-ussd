package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.RsaOptionsStageHandler;
import com.swifta.ussd.service.screens.TAndCStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TAndCStageHandlerTest {

    private TAndCStageHandler tAndCStageHandler;
    @Before
    public void setUp() throws Exception {
        tAndCStageHandler = new TAndCStageHandler();
    }

    @Test
    public void processStageCancle() {
        String expected = T_AND_C_DECLINE;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("2");

        tAndCStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStage() {
        String expected = DOB;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        tAndCStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = tAndCStageHandler.getStage();
        assertEquals(Stage.T_AND_C, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "I identify MTN and EmpayIT. I accept the T&C in accordance to EmpayIT policy\n1. Yes\n2. No";

        USSDResponse ussdResponse = tAndCStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
