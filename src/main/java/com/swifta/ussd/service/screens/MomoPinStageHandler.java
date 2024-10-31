package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.MOMO_PIN_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.PAYMENT_OPTION_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class MomoPinStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(PAYMENT_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return MOMO_PIN;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MOMO_PIN_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
