package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.TICKET_MODE_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.FLOW;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TicketModeStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(getNextStageByFlow(session));
    }

    private String getNextStageByFlow(UssdSession session) {
        if(session.getData(FLOW).equalsIgnoreCase("resend_ticket")) {
            return TICKET_LIST;
        } else {
            if(PURCHASE_OPTION_TYPE.equalsIgnoreCase("self")) {
                return NUMBER_OF_TICKET;
            }
            return PHONE;
        }
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
