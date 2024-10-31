package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.NUMBER_OF_TICKET_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.PHONE_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class NumberOfTicketStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(TICKET_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return NUMBER_OF_TICKET;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(NUMBER_OF_TICKET_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
