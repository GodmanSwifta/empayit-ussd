package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.EventTypeData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.model.EventTypeMenuModel;
import com.swifta.ussd.model.MenuPageStore;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.swifta.ussd.constant.AppMessages.EVENT_TYPE_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.EVENT_TYPE_VALUE;
import static com.swifta.ussd.constant.PropertyKeys.PAGE_NO;
import static com.swifta.ussd.constant.Stage.*;
import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventTypeStageHandler implements StageHandler {

    private final UssdProductService productService;

    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());
        if (input < 0) {
            session.setCurrentStage(INVALID_INPUT);
            return;
        }
        String page = session.getData(PAGE_NO);

        switch (input) {
            case 0:
                if (isNull(page) || Integer.parseInt(page) == 1) {
                    session.setCurrentStage(MAIN_MENU);
                    cleanUp(session);
                    return;
                }
                session.setData(PAGE_NO, String.valueOf(Integer.parseInt(page) - 1));
                break;
            case 99:
                if (isNull(page)) {
                    session.setCurrentStage(INVALID_INPUT);
                    return;
                }
                session.setData(PAGE_NO, String.valueOf(Integer.parseInt(page) + 1));
                break;
            default:
                EventTypeData selected = session.getMenuPageStore().getMenuPageModel(input).getObject();
                session.setData(EVENT_TYPE_VALUE, selected.getEventName());
                session.setCurrentStage(EVENT_OPTION);
                cleanUp(session);
        }
    }


    @Override
    public String getStage() {
        return EVENT_TYPE;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {

        String page = session.getData(PAGE_NO);
        if (isNull(page)) {
            setupPageItems(session);//TODO: TEST
            session.setData(PAGE_NO, "1");
        }
        int pageNo = isNull(page) ? 1 : Integer.parseInt(page);
        String menu = session.getMenuPageStore().getPage(pageNo);
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(menu)
                .freeflow(Freeflow.FC)
                .build();
    }

    private void setupPageItems(UssdSession session) {
        List<EventTypeData> eventTypes = productService.getAllEventTypes();
        MenuPageStore store = new MenuPageStore(
                EVENT_TYPE_MESSAGE,
                eventTypes.stream()
                        .map(EventTypeMenuModel::new)
                        .collect(Collectors.toList())
        );
        session.setMenuPageStore(store);
    }

    private void cleanUp(UssdSession session) {
        session.setData(PAGE_NO, null);
    }
}
