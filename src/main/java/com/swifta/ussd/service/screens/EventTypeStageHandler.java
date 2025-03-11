package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;
import static com.swifta.ussd.constant.AppMessages.EVENT_TYPE_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.EVENT_TYPE_VALUE;
import static com.swifta.ussd.constant.Stage.*;


@Component
public class EventTypeStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        session.setCurrentStage(EVENT_OPTION);
        switch (input) {
            case "1":
                session.setData(EVENT_TYPE_VALUE, "Transport");
                break;

            case "2":
                session.setData(EVENT_TYPE_VALUE, "Events");
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);
        }
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
                .freeflow(Freeflow.FC)
                .build();
    }
}
