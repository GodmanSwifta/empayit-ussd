package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.ResendTicketService;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.TICKET_RESEND_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.Stage.TICKET_RESEND_CONFIRMATION;
import static com.swifta.ussd.constant.Stage.TICKET_SENT;

@Component
public class TicketResendConfirmationStageHandler implements StageHandler {
    private final ResendTicketService resendTicketService;

    public TicketResendConfirmationStageHandler(ResendTicketService resendTicketService) {
        this.resendTicketService = resendTicketService;
    }

    @Override
    public void processStage(UssdSession session) {
        String phoneNumber = session.getMsisdn();
        try{
            resendTicketService.resendTicket(phoneNumber);
        } catch (Exception e) {
            throw new RuntimeException("Ticket resend failed");
        }
        session.setCurrentStage(TICKET_SENT);
    }

    @Override
    public String getStage() {
        return TICKET_RESEND_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(String.format(TICKET_RESEND_CONFIRMATION_MESSAGE, session.getMsisdn()))
                .freeflow(Freeflow.FC)
                .build();
    }
}
