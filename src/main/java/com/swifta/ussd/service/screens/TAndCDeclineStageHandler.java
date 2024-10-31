package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.T_AND_C_DECLINE_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.T_AND_C_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TAndCDeclineStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        //emty
    }

    @Override
    public String getStage() {
        return T_AND_C_DECLINE;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(T_AND_C_DECLINE_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
