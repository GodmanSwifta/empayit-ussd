package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.PHONE_INVALID_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.PHONE_MESSAGE;
import static com.swifta.ussd.constant.Stage.PHONE;
import static com.swifta.ussd.constant.Stage.PHONE_INVALID;

@Component
public class PhoneInvalidStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //empty
    }

    @Override
    public String getStage() {
        return PHONE_INVALID;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(PHONE_INVALID_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
