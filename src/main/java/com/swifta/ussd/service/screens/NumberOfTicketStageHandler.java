package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.*;
import static com.swifta.ussd.constant.PropertyKeys.NUMBER_OF_TICKET_RETRY;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class NumberOfTicketStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String noOfTicket = session.getUssdInput();
        if(isValid(noOfTicket, session)) {
            session.setData(NUMBER_OF_TICKET_RETRY, "false");
            session.setCurrentStage(TICKET_CONFIRMATION);
        }
        session.setData(NUMBER_OF_TICKET_RETRY, "true");

    }

    private boolean isValid(String noOfTicket, UssdSession session) {


        return true;
    }


    @Override
    public String getStage() {
        return NUMBER_OF_TICKET;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String  message = NUMBER_OF_TICKET_MESSAGE;
        if(session.getData(NUMBER_OF_TICKET_RETRY) != null ) {
            message = session.getData(NUMBER_OF_TICKET_RETRY).equalsIgnoreCase("true") ? NUMBER_OF_TICKET_RETRY_MESSAGE : NUMBER_OF_TICKET_MESSAGE;
        }
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FC)
                .build();
    }
}
