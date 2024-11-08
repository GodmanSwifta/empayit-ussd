package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.EventOptionStageHandler;
import com.swifta.ussd.service.screens.MainMenuStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MainMenuStageHandlerTest {

    private MainMenuStageHandler mainMenuStageHandler;
    @Before
    public void setUp() throws Exception {
        mainMenuStageHandler = new MainMenuStageHandler();
    }

    @Test
    public void processStageForPurchase() {
        String expected = PURCHASE_OPTION;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        mainMenuStageHandler.processStage(session);

        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStageForResnd() {
        String expected = PHONE;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("2");

        mainMenuStageHandler.processStage(session);

        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = MAIN_MENU;
        assertEquals(stage, mainMenuStageHandler.getStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Welcome to EmpayIT\n1. Purchase Ticket\n2. ------- Ticket\n3. ------ Ticket\n4. ------ Ticket";

        USSDResponse ussdResponse = mainMenuStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
