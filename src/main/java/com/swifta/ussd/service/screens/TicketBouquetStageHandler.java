package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.TicketBouquetData;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.enums.BuyerType;
import com.swifta.ussd.model.MenuPageStore;
import com.swifta.ussd.model.TicketBouquetMenuModel;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.UssdProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.swifta.ussd.constant.AppMessages.TICKET_BOUQUET_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;
import static java.util.Objects.isNull;

@Component
public class TicketBouquetStageHandler implements StageHandler {

    private final UssdProductService productService;

    public TicketBouquetStageHandler(UssdProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processStage(UssdSession session) {
//        session.setCurrentStage(getNextStageByFlow(session));

        int input = Integer.parseInt(session.getUssdInput());
        if (input < 0) {
            session.setCurrentStage(INVALID_INPUT);
            return;
        }
        String page = session.getData(PAGE_NO);

        switch (input) {
            case 0:
                if (isNull(page) || Integer.parseInt(page) == 1) {
                    session.setCurrentStage(EVENT_TYPE);
                    cleanUp(session);
                    return;
                }
                session.setData(PAGE_NO, String.valueOf(Integer.parseInt(page) - 1));
                break;

            case 99:
                if (isNull(page)) {
                    session.setCurrentStage(INVALID_INPUT);
                    cleanUp(session);
                    return;
                }
                session.setData(PAGE_NO, String.valueOf(Integer.parseInt(page)) + 1);
                break;

            default:
                TicketBouquetData selected = session.getMenuPageStore().getMenuPageModel(input).getObject();
                session.setData(BOUQUET_NAME, selected.getName());
                session.setData(BOUQUET_OPTION_VALUE, selected.getId());
                session.setData(AMOUNT, selected.getAmount().toPlainString());
                BuyerType buyerType = BuyerType.valueOf(session.getData(PURCHASE_OPTION_TYPE));
                String nextStage = PHONE;
                if (buyerType.equals(BuyerType.CUSTOMER)) {
                    nextStage = NUMBER_OF_TICKET;
                }
                session.setCurrentStage(nextStage);
                cleanUp(session);

        }
    }

    private String getNextStageByFlow(UssdSession session) {
        if (session.getData(FLOW).equalsIgnoreCase("resend_ticket")) {
            return TICKET_LIST;
        } else {
            BuyerType buyerType = BuyerType.valueOf(session.getData(PURCHASE_OPTION_TYPE));
            if (buyerType.equals(BuyerType.CUSTOMER)) {
                return NUMBER_OF_TICKET;
            }
            return PHONE;
        }
    }

    @Override
    public String getStage() {
        return TICKET_BOUQUET;
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
        List<TicketBouquetData> data = productService.getTicketBouquetsByEventId(session.getData(EVENT_OPTION_VALUE));
        MenuPageStore store = new MenuPageStore(
                TICKET_BOUQUET_MESSAGE,
                data.stream()
                        .map(TicketBouquetMenuModel::new)
                        .collect(Collectors.toList())
        );
        session.setMenuPageStore(store);
    }

    private void cleanUp(UssdSession session) {
        session.setData(PAGE_NO, null);
    }
}
