package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.EventOptionStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.swifta.ussd.constant.Stage.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventOptionStageHandlerTest {

    private EventOptionStageHandler eventOptionStageHandler;
    @Before
    public void setUp() throws Exception {
        eventOptionStageHandler = new EventOptionStageHandler();
    }

    @Test
    public void processStageForSport() {
        String expected = SPORT;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("2");

        eventOptionStageHandler.processStage(session);

        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void processStageForMovie() {
        String expected = MOVIE_TICKET_OPTION;

        UssdSession session = MockGenerator.generateSession("id");
        session.setUssdInput("4");

        eventOptionStageHandler.processStage(session);

        assertEquals(expected, session.getCurrentStage());
    }

    @Test
    public void getStage() {
        String stage = EVENT_OPTION;
        assertEquals(EVENT_OPTION, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Event\n1. Concert\n2. Sporting Events\n3. Theatre\n4. Movies";

        USSDResponse ussdResponse = eventOptionStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
