package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.ResendTicketService;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.TICKET_RESEND_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TicketResendConfirmationStageHandler implements StageHandler {


    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());
        if(input< 0){
            session.setCurrentStage(INVALID_INPUT);
            return;
        }

        switch(input){
            case 1:
                session.setCurrentStage(TICKET_SENT);
                break;
            case 2:
                session.setCurrentStage(MAIN_MENU);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);
                break;
        }



    }

    @Override
    public String getStage() {
        return TICKET_RESEND_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String phone = session.getData(PHONE);

        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(String.format(TICKET_RESEND_CONFIRMATION_MESSAGE, session.getMsisdn()))
                .freeflow(Freeflow.FC)
                .build();
    }
}
