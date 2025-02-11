package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.*;
import static com.swifta.ussd.constant.PropertyKeys.PAYMENT_METHOD;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class PaymentChannelStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(PAYMENT_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return PAYMENT_CHANNEL;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String message = null;
        if(session.getData(PURCHASE_OPTION_TYPE).equalsIgnoreCase("agent")){
            message = MOMO_PIN_MESSAGE;
        } else {
            String option = session.getData(PAYMENT_METHOD);
            message = option.equalsIgnoreCase("mtn_momo") ? MOMO_PIN_MESSAGE : PROMOCODE_MESSAGE;
        }
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FC)
                .build();
    }
}
