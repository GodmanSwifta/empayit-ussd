package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.AgentData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.enums.BuyerType;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.EmpayItOnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.AppMessages.*;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;

@Component
@RequiredArgsConstructor
public class PurchaseOptionStageHandler implements StageHandler {
    private final EmpayItOnboardingService empayItOnboardingService;
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();

        switch (input) {
            case "1":
                session.setData(BUYER_ID, session.getData(CUSTOMER_ID));
                session.setData(PURCHASE_OPTION_TYPE, BuyerType.CUSTOMER.name());
                break;
            case "2":
                AgentData agentData = empayItOnboardingService.validateAgentByPhone(session.getMsisdn());
                if(agentData == null) {
                    session.setCurrentStage(INVALID_AGENT);
                    return;
                }
                session.setData(BUYER_ID, agentData.getUserId());
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
