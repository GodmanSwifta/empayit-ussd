package com.swifta.ussd.service.screens;

import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.dto.request.CreateCustomerRequest;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import com.swifta.ussd.serviceClient.EmpayItOnboardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.swifta.ussd.constant.AppMessages.KYC_CONFIRMATION_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.*;

@Slf4j
@Component
//@RequiredArgsConstructor
public class KycConfirmationStageHandler implements StageHandler {
    private final EmpayItOnboardingService empayItOnboardingService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    public KycConfirmationStageHandler(EmpayItOnboardingService empayItOnboardingService) {
        this.empayItOnboardingService = empayItOnboardingService;
    }


    @Override
    public void processStage(UssdSession session) {
        int input = Integer.parseInt(session.getUssdInput());

        switch (input) {
            case 1:
                String phoneNumber = session.getMsisdn();

                CreateCustomerRequest customerRequest = new CreateCustomerRequest();
                customerRequest.setPhone(phoneNumber);
                customerRequest.setFirstName(session.getData(FIRST_NAME));
                customerRequest.setLastName(session.getData(LAST_NAME));

                CustomerData newCustomer = empayItOnboardingService.createCustomer(customerRequest);

                if (newCustomer != null) {
                    session.setData(USER_EXIST, "1");
                    session.setData(CUSTOMER_ID, newCustomer.getUserId());
                    session.setData(FIRST_NAME, newCustomer.getFirstName());
                    session.setData(LAST_NAME, newCustomer.getLastName());

                }
                session.setCurrentStage(KYC_VALID);
                break;
            case 2:
                session.setCurrentStage(KYC_INVALID);
                break;
            default:
                session.setCurrentStage(INVALID_INPUT);

        }
    }

    @Override
    public String getStage() {
        return KYC_CONFIRMATION;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {

        String firstName = session.getData(FIRST_NAME);
        String lastName = session.getData(LAST_NAME);
        String dob = session.getData(CUSTOMER_DOB);
        LocalDate dobDate = LocalDate.parse(dob, dateTimeFormatter);
        dob = dobDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String fullName = firstName + " " + lastName;

        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(MessageFormat.format(KYC_CONFIRMATION_MESSAGE, fullName, dob))
                .freeflow(Freeflow.FC)
                .build();
    }
}
