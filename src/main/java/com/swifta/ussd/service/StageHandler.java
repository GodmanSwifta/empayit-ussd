package com.swifta.ussd.service;


import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;

public interface StageHandler {

    void processStage(UssdSession session);

    String getStage();

    USSDResponse loadPage(UssdSession session);
}
