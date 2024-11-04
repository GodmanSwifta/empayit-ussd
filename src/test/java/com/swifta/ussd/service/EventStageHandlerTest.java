package com.swifta.ussd.service;

import com.swifta.ussd.constant.PropertyKeys;
import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.DobStageHandler;
import com.swifta.ussd.service.screens.EventStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventStageHandlerTest {

    private EventStageHandler eventStageHandler;
    @Before
    public void setUp() throws Exception {
        eventStageHandler = new EventStageHandler();
    }

    @Test
    public void getStage() {
        String stage = eventStageHandler.getStage();
        assertEquals(Stage.EVENT, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Event\n1. -----\n2. ------\n3. ------\n4. ------";

        USSDResponse ussdResponse = eventStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
