package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.EventOptionStageHandler;
import com.swifta.ussd.service.screens.MainMenuStageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MainMenuStageHandlerTest {

    private MainMenuStageHandler mainMenuStageHandler;

    @Mock
    private UssdProductService ussdProductService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainMenuStageHandler = new MainMenuStageHandler(ussdProductService);
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
        String expected = "Welcome to EmpayIT\n1. Purchase Ticket\n2. Resend Ticket\n3. Refund Ticket\n4. Ticket Validation\n5. Contact Us";

        USSDResponse ussdResponse = mainMenuStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
