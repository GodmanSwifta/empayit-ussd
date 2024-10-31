package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.CANCLE_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.MAIN_MENU_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class MainMenuStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(PURCHASE_OPTION);
    }

    @Override
    public String getStage() {
        return MAIN_MENU;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MAIN_MENU_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
