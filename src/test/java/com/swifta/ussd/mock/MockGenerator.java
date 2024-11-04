package com.swifta.ussd.mock;




//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import com.swifta.ussd.dto.USSDRequest;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;

public class MockGenerator {

    public static USSDRequest generateUSSDRequest() {
        USSDRequest ussdRequest = new USSDRequest();
        ussdRequest.setMsisdn("67y3r748495");
        ussdRequest.setNewRequest(1);
        ussdRequest.setSubscriberInput("yes");
        return ussdRequest;
    }

    public static USSDResponse generateUssdResponse() {
        USSDResponse ussdResponse = new USSDResponse();
        ussdResponse.setMsisdn("0801111111");
        return ussdResponse;
    }

    public static UssdSession generateSession(String sessionId) {
        UssdSession ussdSession = new UssdSession();
        ussdSession.setSessionId(sessionId);
        ussdSession.setMsisdn("0801111111");

        return ussdSession;
    }

}
