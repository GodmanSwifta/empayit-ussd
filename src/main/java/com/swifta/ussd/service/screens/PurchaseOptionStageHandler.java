package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.EventTypeData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.enums.BuyerType;
import com.swifta.ussd.model.EventTypeMenuModel;
import com.swifta.ussd.model.MenuPageStore;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.swifta.ussd.constant.AppMessages.*;
import static com.swifta.ussd.constant.PropertyKeys.PURCHASE_OPTION_TYPE;
import static com.swifta.ussd.constant.Stage.*;

@Component
@RequiredArgsConstructor
public class PurchaseOptionStageHandler implements StageHandler {

    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();

        switch (input) {
            case "1":
                session.setData(PURCHASE_OPTION_TYPE, BuyerType.CUSTOMER.name());
                break;
            case "2":
                session.setData(PURCHASE_OPTION_TYPE,  BuyerType.AGENT.name());
                break;
        }
        session.setCurrentStage(EVENT_TYPE);
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
