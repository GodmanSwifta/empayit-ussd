package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.*;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.model.EventOptionMenuModel;
import com.swifta.ussd.model.EventTypeMenuModel;
import com.swifta.ussd.model.MenuPageStore;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.swifta.ussd.constant.AppMessages.EVENT_OPTION_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.EVENT_TYPE_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;
import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOptionStageHandler implements StageHandler {

    private final UssdProductService productService;
    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());
        if (input < 0) {
            session.setCurrentStage(INVALID_INPUT);
            return;
        }
        String page = session.getData(PAGE_NO);

        switch(input){
            case 0 :
                if(isNull(page) || Integer.parseInt(page) == 1){
                    session.setCurrentStage(EVENT_TYPE);
                    cleanUp(session);
                    return;
                }
                session.setData(PAGE_NO, String.valueOf(Integer.parseInt(page) -1));
                break;

            case 99 :
                if(isNull(page)){
                    session.setCurrentStage(INVALID_INPUT);
                    cleanUp(session);
                    return;
                }
                session.setData(PAGE_NO, String.valueOf(Integer.parseInt(page)) +1);
                break;

            default:
                EventOptionData selected = session.getMenuPageStore().getMenuPageModel(input).getObject();
                session.setData(EVENT_OPTION_VALUE, selected.getOptionName());
                session.setCurrentStage(EVENT_OPTION);
                cleanUp(session);

        }

    }
    private String getNextStage(String input, UssdSession session) {
        String nextStage = null;
        switch (input) {
            case "1":
                session.setData(EVENT_OPTION_VALUE, "Coconut");
                break;
            case "2":
                session.setData(EVENT_OPTION_VALUE, "Sport");
                nextStage = SPORT;
                break;
            case "3":
                session.setData(EVENT_OPTION_VALUE, "Theatre");
                break;
            case "4":
                session.setData(EVENT_OPTION_VALUE, "Movie");
                nextStage = MOVIE_TICKET_OPTION;
                break;
            default:
        }
        return nextStage;
    }

    @Override
    public String getStage() {
        return EVENT_OPTION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String page = session.getData(PAGE_NO);
        if(isNull(page)){
            setupPageItems(session);
            session.setData(PAGE_NO, "1");

        }

        int pageNo = isNull(page) ? 1 : Integer.parseInt(page);
        String menu = session.getMenuPageStore().getPage(pageNo);
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(menu)
                .freeflow(Freeflow.FC)
                .build()
                ;
    }

    private void setupPageItems(UssdSession session) {
        String eventType = session.getData(EVENT_TYPE_VALUE);
        List<EventOptionData> eventOptions = productService.getEventsByType(eventType);

        MenuPageStore store = new MenuPageStore(
                EVENT_OPTION_MESSAGE,
                eventOptions.stream()
                        .map(EventOptionMenuModel::new)
                        .collect(Collectors.toList())
        );
        session.setMenuPageStore(store);
    }



    private void cleanUp(UssdSession session) {
        session.setData(PAGE_NO, null);
    }
}
