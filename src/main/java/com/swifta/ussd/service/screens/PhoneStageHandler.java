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

        String phone = session.getUssdInput().trim();
        if(isValid(phone)) {
            session.setData(PHONE_RETRY, "false");
//            session.setData(PHONE, phone.replaceFirst("0", "234"));
            session.setData(PHONE, phone);
            session.setCurrentStage(getNextStageByFlow(session));
            return;
        }
        session.setData(PHONE_RETRY, "true");


    }
    private boolean isValid(String phone) {
        if (phone.length()!=11) {
            return false;
        }
        if(!phone.startsWith("0")){
            return false;
        }
        return true;
    }



    private String getNextStageByFlow(UssdSession session) {
        if(session.getData(FLOW).equalsIgnoreCase("resend_ticket")) {
            return TICKET_LIST;
        } else {
            return NUMBER_OF_TICKET;
        }
    }

    @Override
    public String getStage() {
        return PHONE;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String  message = PHONE_MESSAGE;
        if(session.getData(PHONE_RETRY) != null ) {
            message = session.getData(PHONE_RETRY).equalsIgnoreCase("true") ? PHONE_RETRY_MESSAGE : PHONE_MESSAGE;
        }
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FC)
                .build();
    }
}
