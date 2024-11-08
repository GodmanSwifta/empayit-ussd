package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.EVENT_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.EVENT_OPTION_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.EVENT_OPTION_VALUE;
//import static com.swifta.ussd.constant.PropertyKeys.TICKET_OPTION;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class EventOptionStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        session.setCurrentStage(getNextStage(input, session));
    }

    private String getNextStage(String input, UssdSession session) {
        String nextStage = null;
        switch (input) {
            case "1":
                session.setData(EVENT_OPTION_VALUE, "Coconut");
                break;
            case "2":
                session.setData(EVENT_OPTION_VALUE, "Sport");
                nextStage = SPORT;
                break;
            case "3":
                session.setData(EVENT_OPTION_VALUE, "Theatre");
                break;
            case "4":
                session.setData(EVENT_OPTION_VALUE, "Movie");
                nextStage = MOVIE_TICKET_OPTION;
                break;
            default:
        }
        return nextStage;
    }

    @Override
    public String getStage() {
        return EVENT_OPTION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(EVENT_OPTION_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
