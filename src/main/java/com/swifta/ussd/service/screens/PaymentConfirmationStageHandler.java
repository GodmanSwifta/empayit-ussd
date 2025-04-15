package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.PAYMENT_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class PaymentConfirmationStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(SMS_TICKET_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return PAYMENT_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(String.format(PAYMENT_CONFIRMATION_MESSAGE, session.getData(AMOUNT), session.getData(TRANSACTION_ID)))
                .freeflow(Freeflow.FC)
                .build();
    }
}
