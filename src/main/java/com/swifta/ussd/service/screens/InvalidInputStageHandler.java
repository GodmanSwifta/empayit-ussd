package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.PropertyKeys.INVALID_INPUT_MESSAGE;
import static com.swifta.ussd.constant.Stage.INVALID_INPUT;

@Component
public class InvalidInputStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //nothing
    }

    @Override
    public String getStage() {
        return INVALID_INPUT;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String message = session.getData(INVALID_INPUT_MESSAGE, "Invalid Input");
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FB)
                .build();
    }
}
