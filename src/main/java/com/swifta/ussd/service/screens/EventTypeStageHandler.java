package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.EVENT_TYPE_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.PURCHASE_OPTION_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class EventTypeStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(EVENT);
    }

    @Override
    public String getStage() {
        return EVENT_TYPE;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(EVENT_TYPE_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
