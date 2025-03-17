package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.EventTypeStageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventTypeStageHandlerTest {

    @Mock
    private UssdProductService productService;
    private EventTypeStageHandler eventTypeStageHandler;
    @Before
    public void setUp() throws Exception {
        eventTypeStageHandler = new EventTypeStageHandler(productService);
    }

    @Test
    public void getStage() {
        String stage = eventTypeStageHandler.getStage();
        assertEquals(Stage.EVENT_TYPE, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select Event type\n1. Transport\n2. Event";

        USSDResponse ussdResponse = eventTypeStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
