package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.PAYMENT_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.SMS_TICKET_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.Stage.PAYMENT_CONFIRMATION;
import static com.swifta.ussd.constant.Stage.SMS_TICKET_CONFIRMATION;

@Component
public class SmsTicketConfirmationStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //empty
    }

    @Override
    public String getStage() {
        return SMS_TICKET_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(SMS_TICKET_CONFIRMATION_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
