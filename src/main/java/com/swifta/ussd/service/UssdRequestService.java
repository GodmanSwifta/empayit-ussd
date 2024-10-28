package com.swifta.ussd.service;

import com.swifta.ussd.dto.USSDRequest;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UssdRequestService {

    private final Map<String, StageHandler> stageHandlerMap = new HashMap<>();

    public UssdRequestService(List<StageHandler> stageHandlers) {
        stageHandlers.forEach(stageHandler -> stageHandlerMap.put(stageHandler.getStage(), stageHandler));
    }

    public USSDResponse handleRequest(final UssdSession ussdSession, final USSDRequest ussdRequest) {
        ussdSession.setUssdInput(ussdRequest.getSubscriberInput());
        stageHandlerMap.get(ussdSession.getCurrentStage()).processStage(ussdSession);

        return stageHandlerMap.get(ussdSession.getCurrentStage()).loadPage(ussdSession);
    }
}
