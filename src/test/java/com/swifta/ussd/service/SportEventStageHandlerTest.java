package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.SportCategoryStageHandler;
import com.swifta.ussd.service.screens.SportEventStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SportEventStageHandlerTest {

    private SportEventStageHandler sportEventStageHandler;
    @Before
    public void setUp() throws Exception {
        sportEventStageHandler = new SportEventStageHandler();
    }

    @Test
    public void processStage() {
        String expected = SPORT_CATEGORY;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("1");

        sportEventStageHandler.processStage(session);
        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = SPORT_EVENT;
        assertEquals(stage, sportEventStageHandler.getStage());
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Event\n1. National Cup Qualifier\n2. ------";

        USSDResponse ussdResponse = sportEventStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
