//package com.swifta.ussd.service.screens;
//
//import com.swifta.ussd.constant.PropertyKeys;
//import com.swifta.ussd.dto.Freeflow;
//import com.swifta.ussd.dto.USSDResponse;
//import com.swifta.ussd.entity.cache.UssdSession;
//import com.swifta.ussd.service.StageHandler;
//import org.springframework.stereotype.Component;
//
//import java.text.MessageFormat;
//
//import static com.swifta.ussd.constant.AppMessages.EVENT_MESSAGE;
//import static com.swifta.ussd.constant.Stage.*;
//
//@Component
//public class EventStageHandler implements StageHandler {
//    @Override
//    public void processStage(UssdSession session) {
//        String input = session.getUssdInput();
//        session.setCurrentStage(getNextStage(input));
//        session.setCurrentStage(MOVIE_TICKET_OPTION);
//    }
//
//    private String getNextStage(String input) {
//        return null;
//    }
//
//    @Override
//    public String getStage() {
////        return EVENT;
//        return null;
//    }
//
//    @Override
//    public USSDResponse loadPage(UssdSession session) {
//        String option = session.getData(PropertyKeys.EVENT_OPTION_VALUE);
//        return USSDResponse.builder()
//                .msisdn(session.getMsisdn())
//                .applicationResponse(MessageFormat.format(EVENT_MESSAGE, option))
//                .freeflow(Freeflow.FB)
//                .build();
//    }
//}
