package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.*;
import static com.swifta.ussd.constant.AppMessages.NUMBER_OF_TICKET_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class PhoneStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {

        String phone = session.getUssdInput();
        if(isValid(phone, session)) {
            session.setData(PHONE_RETRY, "false");
            session.setCurrentStage(NUMBER_OF_TICKET);
        }
        session.setData(PHONE_RETRY, "true");
    }

    private boolean isValid(String noOfTicket, UssdSession session) {


        return true;
    }

    @Override
    public String getStage() {
        return PHONE;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String  message = session.getData(PHONE_RETRY).equalsIgnoreCase("true") ? PHONE_RETRY_MESSAGE : PHONE_MESSAGE;
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FC)
                .build();
    }
}
