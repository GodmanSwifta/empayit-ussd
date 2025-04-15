package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.ACKNOWLEDGMENT_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class AcknowledgmentStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());

        switch (input) {
            case 1:
                session.setCurrentStage(T_AND_C);
                break;
            case 2:
                session.setCurrentStage(CANCEL);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);

        }
    }

    @Override
    public String getStage() {
        return ACKNOWLEDGMENT_OPTIONS;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(ACKNOWLEDGMENT_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
