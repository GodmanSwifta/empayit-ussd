package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.EventStageHandler;
import com.swifta.ussd.service.screens.MomoPinStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MomoPinStageHandlerTest {

    private MomoPinStageHandler momoPinStageHandler;
    @Before
    public void setUp() throws Exception {
        momoPinStageHandler = new MomoPinStageHandler();
    }

    @Test
    public void getStage() {
        String stage = momoPinStageHandler.getStage();
        assertEquals(Stage.MOMO_PIN, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Enter MoMo Pin";

        USSDResponse ussdResponse = momoPinStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
