package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.*;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.enums.BuyerType;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.TICKET_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;

@Component
public class TicketConfirmationStageHandler implements StageHandler {

    private final UssdProductService ussdProductService;

    public TicketConfirmationStageHandler(UssdProductService ussdProductService) {
        this.ussdProductService = ussdProductService;
    }

    @Override
    public void processStage(UssdSession session) {
        switch (Integer.parseInt(session.getUssdInput())) {
            case 1:
                createTicketTransaction(session);
                break;
            case 2:
                session.setCurrentStage(MAIN_MENU);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);
        }
    }

    private void createTicketTransaction(UssdSession session) {
        BuyerType buyerType = BuyerType.valueOf(session.getData(PURCHASE_OPTION_TYPE));

        CreateTransactionResponse transaction = ussdProductService.createTransaction(CreateTransactionRequest.builder()
                .type(buyerType)
                .ticketCount(Integer.parseInt(session.getData(TICKET_COUNT)))
                .buyerId(session.getData(CUSTOMER_ID))
                .eventId(session.getData(EVENT_OPTION_VALUE))
                .bouquetId(session.getData(BOUQUET_OPTION_VALUE))
                .build());
        session.setData(TRANSACTION_REFERENCE, transaction.getReference());
        session.setData(TRANSACTION_ID, transaction.getTransactionId());

        switch (buyerType) {
            case CUSTOMER -> session.setCurrentStage(PAYMENT_OPTION);
            case AGENT -> session.setCurrentStage(PAYMENT_CHANNEL);
        }
    }

    @Override
    public String getStage() {
        return TICKET_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(String.format(TICKET_CONFIRMATION_MESSAGE,
                        session.getData(EVENT_TYPE_VALUE),
                        session.getData(EVENT_NAME),
                        session.getData(EVENT_DATE),
                        session.getData(TICKET_COUNT),
                        session.getData(AMOUNT)))
                .freeflow(Freeflow.FC)
                .build();
    }
}
