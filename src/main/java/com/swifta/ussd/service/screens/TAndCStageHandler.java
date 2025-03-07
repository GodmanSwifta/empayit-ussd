package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.T_AND_C_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.DOB_RETRY;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TAndCStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());

        switch (input) {
            case 1:
                session.setData(DOB_RETRY, "false");
                //TODO (GODMAN): CALL SIM-REG API AND CONSTRUCT CREATE CUSTOMER REQUEST
                session.setCurrentStage(DOB);
                break;
            case 2:
                session.setCurrentStage(T_AND_C_DECLINE);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);

        }
    }

    @Override
    public String getStage() {
        return T_AND_C;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(T_AND_C_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
