package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.PAYMENT_OPTION_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.TICKET_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.PAYMENT_METHOD;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class PaymentOptionStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        setStageParameters(input, session);
        session.setCurrentStage(PAYMENT_CHANNEL);
    }

    private void setStageParameters(String input, UssdSession session) {
        switch (input) {
            case "1":
                session.setData(PAYMENT_METHOD, "mtn_momo");
                break;
            case "2":
                session.setData(PAYMENT_METHOD, "promocode");
                break;
        }
    }

    @Override
    public String getStage() {
        return PAYMENT_OPTION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(PAYMENT_OPTION_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
