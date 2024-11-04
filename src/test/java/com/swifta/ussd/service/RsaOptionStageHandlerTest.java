package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.PurchaseOptionStageHandler;
import com.swifta.ussd.service.screens.RsaOptionsStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RsaOptionStageHandlerTest {

    private RsaOptionsStageHandler rsaOptionsStageHandler;
    @Before
    public void setUp() throws Exception {
        rsaOptionsStageHandler = new RsaOptionsStageHandler();
    }

    @Test
    public void processStageCancle() {
        String expected = CANCLE;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("2");

        rsaOptionsStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStageTAndC() {
        String expected = T_AND_C;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        rsaOptionsStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = rsaOptionsStageHandler.getStage();
        assertEquals(Stage.RSA_OPTIONS, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "The service is provided by EmpayIT to enable customer purchase event tickets. \nWould you like to continue?\n1. Proceed \n2. Cancel";

        USSDResponse ussdResponse = rsaOptionsStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
