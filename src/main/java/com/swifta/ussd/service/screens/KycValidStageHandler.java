package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import static com.swifta.ussd.constant.AppMessages.KYC_VALID_MESSAGE;
import static com.swifta.ussd.constant.Stage.KYC_VALID;

@Component
public class KycValidStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //empty
    }

    @Override
    public String getStage() {
        return KYC_VALID;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        //TODO: GET CORRECT NAME FROM SESSION TO REPLACE PLACEHOLDER VALUE
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MessageFormat.format(KYC_VALID_MESSAGE, "John Paul"))
                .freeflow(Freeflow.FB)
                .build();
    }
}
