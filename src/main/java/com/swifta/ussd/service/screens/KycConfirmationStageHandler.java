package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import static com.swifta.ussd.constant.AppMessages.KYC_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class KycConfirmationStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());

        switch (input) {
            case 1:
                //TODO (GODMAN): CALL CREATE CUSTOMER ENDPOINT ON CORE
                session.setCurrentStage(KYC_VALID);
                break;
            case 2:
                session.setCurrentStage(KYC_INVALID);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);

        }
    }

    @Override
    public String getStage() {
        return KYC_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        //TODO (GODMAN): USE CORRECT NAMES
        String name = "John Statan";
        String dob = "1 March, 1998";
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MessageFormat.format(KYC_CONFIRMATION_MESSAGE, name, dob))
                .freeflow(Freeflow.FC)
                .build();
    }
}
