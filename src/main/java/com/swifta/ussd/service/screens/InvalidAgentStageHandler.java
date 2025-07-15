package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.INVALID_AGENT_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class InvalidAgentStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(TICKET_RESEND_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return INVALID_AGENT;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(INVALID_AGENT_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
