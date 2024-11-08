package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.SUPPORT_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.TICKET_LIST_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class SupportStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(TICKET_RESEND_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return SUPPORT;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(SUPPORT_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
