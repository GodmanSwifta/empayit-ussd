package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.SportCategoryStageHandler;
import com.swifta.ussd.service.screens.TicketListStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SportCategoryStageHandlerTest {

    private SportCategoryStageHandler sportCategoryStageHandler;
    @Before
    public void setUp() throws Exception {
        sportCategoryStageHandler = new SportCategoryStageHandler();
    }

    @Test
    public void processStage() {
        String expected = NUMBER_OF_TICKET;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        sportCategoryStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = SPORT_CATEGORY;
        assertEquals(stage, sportCategoryStageHandler.getStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Category\n1. Section A\n2. Section B\n3. Section C";

        USSDResponse ussdResponse = sportCategoryStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
