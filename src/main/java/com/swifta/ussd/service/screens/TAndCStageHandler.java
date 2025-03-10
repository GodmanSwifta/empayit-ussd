package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.dto.request.CreateCustomerRequest;
import com.swifta.ussd.dto.simreg.SimRegInfo;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.service.simreg.SimRegService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.swifta.ussd.constant.AppMessages.T_AND_C_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.CUSTOMER_DOB;
import static com.swifta.ussd.constant.PropertyKeys.DOB_RETRY;
import static com.swifta.ussd.constant.Stage.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TAndCStageHandler implements StageHandler {

    private final SimRegService simRegService;


    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());

        switch (input) {
            case 1:
                session.setData(DOB_RETRY, "false");
                String msisdn = session.getMsisdn();
                SimRegInfo simRegInfo = simRegService.getSimRegInfo(msisdn);

                if(simRegInfo == null){
                    session.getCurrentStage();
                    return;
                }

                CreateCustomerRequest customerRequest = new CreateCustomerRequest();
                customerRequest.setFirstName(simRegInfo.getFirstName());
                customerRequest.setDob(LocalDate.parse(simRegInfo.getDateOfBirth()));
                customerRequest.setPhone(String.valueOf(simRegInfo.getCompleteNumber()));
                customerRequest.setLastName(simRegInfo.getFamilyName());


                session.setCurrentStage(DOB);
                break;
            case 2:
                session.setCurrentStage(T_AND_C_DECLINE);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);

        }
    }

    @Override
    public String getStage() {
        return T_AND_C;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(T_AND_C_MESSAGE)
                .freeflow(Freeflow.FC)
                .build();
    }
}
