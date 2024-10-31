package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.DOB_INVALID_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.DOB_MESSAGE;
import static com.swifta.ussd.constant.Stage.DOB;
import static com.swifta.ussd.constant.Stage.DOB_INVALID;

@Component
public class DobInvalidStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //empty
    }

    @Override
    public String getStage() {
        return DOB_INVALID;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(DOB_INVALID_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
