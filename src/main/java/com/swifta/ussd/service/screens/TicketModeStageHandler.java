package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.EVENT_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.TICKET_MODE_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TicketModeStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(PHONE);
    }

    @Override
    public String getStage() {
        return TICKET_MODE;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(TICKET_MODE_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
