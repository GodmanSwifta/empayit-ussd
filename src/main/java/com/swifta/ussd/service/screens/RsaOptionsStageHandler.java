package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.RSA_OPTIONS_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.INVALID_INPUT_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class RsaOptionsStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String option = session.getUssdInput();
        session.setCurrentStage(getStageOption(option));
    }

    private String getStageOption(String option) {
        return option.equals("2") ? CANCLE : T_AND_C;
    }

    @Override
    public String getStage() {
        return RSA_OPTIONS;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(RSA_OPTIONS_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
