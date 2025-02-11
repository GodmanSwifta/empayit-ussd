package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.CANCLE_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.MAIN_MENU_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.FLOW;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class MainMenuStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        session.setCurrentStage(getNextStage(input, session));
    }

    private String getNextStage(String input, UssdSession session) {
        String nextStage = null;
        switch (input) {
            case "1":
                session.setData(FLOW, "purchase");
                nextStage = PURCHASE_OPTION;
                break;
            case "2":
                session.setData(FLOW, "resend_ticket");
                nextStage = PHONE;
                break;
            case "3":
                session.setData(FLOW, "refund_ticket");
                nextStage = SUPPORT;
                break;
            case "4":
                session.setData(FLOW, "ticket_validation");
                nextStage = SUPPORT;
                break;
            case "5":
                session.setData(FLOW, "contact_us");
                nextStage = SUPPORT;
                break;
            default:
                break;
        }
        return nextStage;
    }

    @Override
    public String getStage() {
        return MAIN_MENU;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        System.out.println("MAIN PAGE");
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MAIN_MENU_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
