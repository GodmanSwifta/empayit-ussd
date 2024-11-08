package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.SPORT_CATEGORY_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.SPORT_EVENT_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class SportCategoryStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(NUMBER_OF_TICKET);
    }

    @Override
    public String getStage() {
        return SPORT_CATEGORY;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(SPORT_CATEGORY_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
