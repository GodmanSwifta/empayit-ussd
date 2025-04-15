package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.InitiatePaymentRequest;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.enums.BuyerType;
import com.swifta.ussd.enums.PaymentStatus;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.MOMO_PIN_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.PROMO_CODE_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.PAYMENT_CHANNEL;
import static com.swifta.ussd.constant.Stage.PAYMENT_CONFIRMATION;

@Component
public class PaymentChannelStageHandler implements StageHandler {
    private final UssdProductService ussdProductService;

    public PaymentChannelStageHandler(UssdProductService ussdProductService) {
        this.ussdProductService = ussdProductService;
    }

    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        var response = ussdProductService.initiatePayment(InitiatePaymentRequest.builder()
                .paymentPin(input)
                .transactionId(session.getData(TRANSACTION_ID))
                .build());
        if (PaymentStatus.FAILED.equals(response.getPaymentStatus())) {
            throw new RuntimeException("Transaction failed");
        }
        session.setCurrentStage(PAYMENT_CONFIRMATION);
    }

    @Override
    public String getStage() {
        return PAYMENT_CHANNEL;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String message = null;
        BuyerType buyerType = BuyerType.valueOf(session.getData(PURCHASE_OPTION_TYPE));
        switch (buyerType) {
            case CUSTOMER -> {
                String option = session.getData(PAYMENT_METHOD);
                message = option.equalsIgnoreCase("mtn_momo") ? MOMO_PIN_MESSAGE : PROMO_CODE_MESSAGE;
            }
            case AGENT -> message = MOMO_PIN_MESSAGE;
        }
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FC)
                .build();
    }
}
