package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.MAIN_MENU_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.PURCHASE_OPTION_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class PurchaseOptionStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        setStageParameters(input, session);
        session.setCurrentStage(EVENT_TYPE);
    }

    private void setStageParameters(String input, UssdSession session) {
        switch (input) {
            case "1":
                session.setData(PURCHASE_OPTION_TYPE, "self");
                break;
            case "2":
                session.setData(PURCHASE_OPTION_TYPE, "agent");
                break;
        }
    }

    @Override
    public String getStage() {
        return PURCHASE_OPTION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(PURCHASE_OPTION_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
