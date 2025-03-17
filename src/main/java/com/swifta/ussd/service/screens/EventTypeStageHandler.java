package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.EventTypeData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import org.springframework.stereotype.Component;

import static com.swifta.ussd.constant.PropertyKeys.EVENT_TYPE_VALUE;
import static com.swifta.ussd.constant.PropertyKeys.PAGE_NO;
import static com.swifta.ussd.constant.Stage.*;
import static java.util.Objects.isNull;


@Component
public class EventTypeStageHandler implements StageHandler {
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

    private void cleanUp(UssdSession session) {
        session.setData(PAGE_NO, null);
    }
}
