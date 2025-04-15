package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.TicketConfirmationStageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketConfirmationStageHandlerTest {

    @Mock
    private UssdProductService ussdProductService;
    private TicketConfirmationStageHandler ticketConfirmationStageHandler;

    @Before
    public void setUp() throws Exception {
        ticketConfirmationStageHandler = new TicketConfirmationStageHandler(ussdProductService);
    }

    @Test
    public void getStage() {
        String stage = ticketConfirmationStageHandler.getStage();
        assertEquals(Stage.TICKET_CONFIRMATION, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Please confirm ticket details\nEvent: Movies\nTitle: Olubirin\nDate: 25th Decemb34 2024\nNumber of ticket: 1\nAmount: N4000\n1. Confirm\n2. Decline";

        USSDResponse ussdResponse = ticketConfirmationStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
