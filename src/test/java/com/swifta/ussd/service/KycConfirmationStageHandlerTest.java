package com.swifta.ussd.service;

import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.mock.MockGenerator;
import com.swifta.ussd.service.screens.KycConfirmationStageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class KycConfirmationStageHandlerTest {

    private KycConfirmationStageHandler kycConfirmationStageHandler;
    @Before
    public void setUp() throws Exception {
        kycConfirmationStageHandler = new KycConfirmationStageHandler();
    }

    @Test
    public void getStage() {
        String stage = kycConfirmationStageHandler.getStage();
        assertEquals(Stage.KYC_CONFIRMATION, stage);
    }

    @Test
    public void loadPage() {
        UssdSession session = MockGenerator.generateSession("id");
        String expected = session.getMsisdn();

        USSDResponse ussdResponse = kycConfirmationStageHandler.loadPage(session);
        assertEquals(expected, ussdResponse.getMsisdn());
    }
}
