/*
package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.*;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class MovieTicketStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        session.setCurrentStage(TICKET_MODE);
    }

    @Override
    public String getStage() {
        return MOVIE_TICKET_OPTION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MOVIE_TICKET_OPTION_MESSAGE)
                .freeflow(Freeflow.FB)
                .build();
    }
}
*/
