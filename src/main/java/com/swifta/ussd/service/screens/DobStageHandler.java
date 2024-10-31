package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.CANCLE_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.DOB_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class DobStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String option = session.getUssdInput();
        session.setCurrentStage(getStageOption(option));
    }

    private String getStageOption(String option) {
        if(isDobValid(option)) {
            return KYC_CONFIRMATION;
        } else {
            return DOB_INVALID;
        }
    }

    private boolean isDobValid(String option) {
        return true;
    }

    @Override
    public String getStage() {
        return DOB;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(DOB_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
