package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import static com.swifta.ussd.constant.AppMessages.DOB_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.KYC_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class KycConfirmationStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //empty
        String option = session.getUssdInput();
        session.setCurrentStage(getConfirmationStageOption(option));
    }

    private String getConfirmationStageOption(String option) {
        return option.equals("1") ? KYC_VALID : KYC_INVALID;
    }

    @Override
    public String getStage() {
        return KYC_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String name = "John Statan";
        String dob = "1 March, 1998";
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MessageFormat.format(KYC_CONFIRMATION_MESSAGE, name, dob))
                .freeflow(Freeflow.FC)
                .build();
    }
}
