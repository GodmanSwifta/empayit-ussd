package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.AcknowledgmentStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AcknowledgmentStageHandlerTest {

    private AcknowledgmentStageHandler acknowledgmentStageHandler;
    @Before
    public void setUp() throws Exception {
        acknowledgmentStageHandler = new AcknowledgmentStageHandler();
    }

    @Test
    public void processStageCancle() {

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("2");

        acknowledgmentStageHandler.processStage(session);
        assertEquals(CANCEL, session.getCurrentStage());
    }

    @Test
    public void processStageTAndC() {

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        acknowledgmentStageHandler.processStage(session);
        assertEquals(T_AND_C, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = acknowledgmentStageHandler.getStage();
        assertEquals(Stage.ACKNOWLEDGMENT_OPTIONS, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "The service is provided by EmpayIT to enable customer purchase event tickets. \nWould you like to continue?\n1. Proceed \n2. Cancel";

        USSDResponse ussdResponse = acknowledgmentStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
