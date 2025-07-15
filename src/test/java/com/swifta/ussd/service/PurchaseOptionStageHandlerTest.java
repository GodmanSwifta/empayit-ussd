package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.PurchaseOptionStageHandler;
import com.swifta.ussd.serviceClient.EmpayItOnboardingService;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOptionStageHandlerTest {

    private PurchaseOptionStageHandler purchaseOptionStageHandler;
    private EmpayItOnboardingService empayItOnboardingService;

    @Before
    public void setUp() throws Exception {
        purchaseOptionStageHandler = new PurchaseOptionStageHandler(empayItOnboardingService);
    }

    @Test
    public void getStage() {
        String stage = purchaseOptionStageHandler.getStage();
        assertEquals(Stage.PURCHASE_OPTION, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = "Select an option\n1. Self Purchase\n2. Agent Purchase";

        USSDResponse ussdResponse = purchaseOptionStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getApplicationResponse());
    }
}
