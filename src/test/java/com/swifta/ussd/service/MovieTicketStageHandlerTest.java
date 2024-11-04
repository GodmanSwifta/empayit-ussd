package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.MomoPinStageHandler;
import com.swifta.ussd.service.screens.MovieTicketStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MovieTicketStageHandlerTest {

    private MovieTicketStageHandler movieTicketStageHandler;
    @Before
    public void setUp() throws Exception {
        movieTicketStageHandler = new MovieTicketStageHandler();
    }

    @Test
    public void getStage() {
        String stage = movieTicketStageHandler.getStage();
        assertEquals(Stage.MOVIE_TICKET_OPTION, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = session.getMsisdn();

        USSDResponse ussdResponse = movieTicketStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getMsisdn());
    }
}
