package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.TICKET_SENT_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.CUSTOMER_NAME;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TicketSentStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(null);
    }

    @Override
    public String getStage() {
        return TICKET_SENT;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String name= session.getData(CUSTOMER_NAME);

        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(TICKET_SENT_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
