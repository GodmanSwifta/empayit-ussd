package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.ResendTicketResponse;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.model.MenuPageStore;
import com.swifta.ussd.model.ResendTicketMenuModel;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.ResendTicketService;
import com.swifta.ussd.serviceClient.SmsService;
import com.swifta.ussd.serviceClient.UssdProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.swifta.ussd.constant.AppMessages.TICKET_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.AppMessages.TICKET_LIST_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;
import static java.util.Objects.isNull;

@Component
public class TicketListStageHandler implements StageHandler {
    private final ResendTicketService resendTicketService;
    private final SmsService smsService;

    public TicketListStageHandler( ResendTicketService resendTicketService, SmsService smsService) {
        this.resendTicketService = resendTicketService;
        this.smsService = smsService;
    }

    @Override
    public void processStage(UssdSession session) {
      int input = Integer.parseInt(session.getUssdInput());
      if(input<0){
          session.setCurrentStage(INVALID_INPUT);
          return;
      }
      String page = session.getData(PAGE_NO);

      switch (input){
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
              ResendTicketResponse selected =session.getMenuPageStore().getMenuPageModel(input).getObject();
              if(selected == null){
                  session.setCurrentStage(INVALID_INPUT);
                  return;
              }
              session.setData(SELECTION_TICKET_ID, selected.getTicketId());
              session.setData(CUSTOMER_NAME, selected.getName());
              //smsService.sendPaymentSms(session);
              session.setCurrentStage(TICKET_RESEND_CONFIRMATION);
              cleanUp(session);
      }
    }



    @Override
    public String getStage() {
        return TICKET_LIST;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String page = session.getData(PAGE_NO);
        if (isNull(page)) {
            setupPageItems(session);
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


    private void setupPageItems(UssdSession session){
        String phone = session.getData(PHONE);
        List<ResendTicketResponse> tickets = resendTicketService.resendTicket(phone);

        if (tickets == null || tickets.isEmpty()) {
            throw new RuntimeException("Ticket not available");
        }

        MenuPageStore store = new MenuPageStore(
                TICKET_LIST_MESSAGE,
                tickets.stream()
                        .map(ResendTicketMenuModel::new)
                        .collect(Collectors.toList())

        );
        session.setMenuPageStore(store);
    }


    private void cleanUp(UssdSession session) {
        session.setData(PAGE_NO, null);
    }
}
