package com.swifta.ussd.service;

import com.swifta.ussd.constant.PropertyKeys;
import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.DobStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.MessageFormat;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DobStageHandlerTest {

    private DobStageHandler dobStageHandler;
    @Before
    public void setUp() throws Exception {
        dobStageHandler = new DobStageHandler();
    }

    @Test
    public void getStage() {
        String stage = dobStageHandler.getStage();
        assertEquals(stage, Stage.DOB);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PropertyKeys.DOB_RETRY, "false");
        String expected = "Enter date of birth";

        USSDResponse ussdResponse = dobStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }

    @Test
    public void loadPageRetry() {
        UssdSession session = MockGenerator.generateSession("id");
        session.setData(PropertyKeys.DOB_RETRY, "true");
        String expected = "Dear Customer,\n\nPlease enter valid Date of Birth";

        USSDResponse ussdResponse = dobStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }

    @Test
    public void processStageValidDob() {
        String expected = KYC_CONFIRMATION;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1985-02-10");

        dobStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStageDobRetry() {
        String expected = DOB;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("3433");

        dobStageHandler.processStage(session);

        assertEquals(expected, session.getCurrentStage());
    }
}
