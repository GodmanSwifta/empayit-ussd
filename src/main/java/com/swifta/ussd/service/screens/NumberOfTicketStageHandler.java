package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.NUMBER_OF_TICKET_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.NUMBER_OF_TICKET_RETRY;
import static com.swifta.ussd.constant.PropertyKeys.TICKET_COUNT;
import static com.swifta.ussd.constant.Stage.NUMBER_OF_TICKET;
import static com.swifta.ussd.constant.Stage.TICKET_CONFIRMATION;

@Component
public class NumberOfTicketStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        int noOfTicket = Integer.parseInt(session.getUssdInput());
        if (noOfTicket < 1) {
            return;
        }
        session.setData(TICKET_COUNT, String.valueOf(noOfTicket));
        session.setCurrentStage(TICKET_CONFIRMATION);
        session.setData(NUMBER_OF_TICKET_RETRY, "true");

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
