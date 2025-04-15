package com.swifta.ussd.service;

import com.swifta.ussd.constant.PropertyKeys;
import com.swifta.ussd.constant.Stage;
import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.USSDRequest;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.repository.UssdSessionRepository;
import com.swifta.ussd.service.screens.InvalidInputStageHandler;
import com.swifta.ussd.service.screens.MainMenuStageHandler;
import com.swifta.ussd.service.screens.AcknowledgmentStageHandler;
import com.swifta.ussd.serviceClient.EmpayItOnboardingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.swifta.ussd.constant.AppMessages.INCORRECT_SHORTCODE_PAGE;
import static com.swifta.ussd.constant.PropertyKeys.*;
import static com.swifta.ussd.constant.Stage.ACKNOWLEDGMENT_OPTIONS;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class UssdServiceImpl implements UssdService {
    private final String shortCode;
    private final UssdRequestService ussdRequestService;
    private final UssdSessionRepository ussdSessionRepository;
    private final InvalidInputStageHandler invalidInputStageHandler;
    private final AcknowledgmentStageHandler acknowledgmentStageHandler;
    private final MainMenuStageHandler mainMenuStageHandler;
    private final EmpayItOnboardingServiceImpl empayItOnboardingService;

    public UssdServiceImpl(
            @Value("${service.short-code}") String shortCode,
            UssdRequestService ussdRequestService,
            UssdSessionRepository ussdSessionRepository,
            InvalidInputStageHandler invalidInputStageHandler, AcknowledgmentStageHandler acknowledgmentStageHandler, MainMenuStageHandler mainMenuStageHandler, EmpayItOnboardingServiceImpl empayItOnboardingService) {
        this.shortCode = shortCode;
        this.ussdRequestService = ussdRequestService;
        this.ussdSessionRepository = ussdSessionRepository;
        this.invalidInputStageHandler = invalidInputStageHandler;
        this.acknowledgmentStageHandler = acknowledgmentStageHandler;
        this.mainMenuStageHandler = mainMenuStageHandler;
        this.empayItOnboardingService = empayItOnboardingService;
    }

    @Override
    public USSDResponse processRequest(USSDRequest request) {
        UssdSession session;
        if (request.getNewRequest() == 1) {
            session = createNewSession(request);

            if (!request.getSubscriberInput().equals(shortCode)) {
                session.setData(PropertyKeys.INVALID_INPUT_MESSAGE, INCORRECT_SHORTCODE_PAGE);
                return invalidInputStageHandler.loadPage(session);
            }
            log.info("new ussd request");
            if (isNewCustomer(session.getMsisdn(), session)) {
                session.setCurrentStage(ACKNOWLEDGMENT_OPTIONS);
                ussdSessionRepository.save(session);
                return acknowledgmentStageHandler.loadPage(session);
            }
            session.setCurrentStage(Stage.MAIN_MENU);
            ussdSessionRepository.save(session);
            return mainMenuStageHandler.loadPage(session);
        }
        log.info("old ussd request");
        session = fetchExistingSession(request);
        log.info("ussd on stage - {} phone - {}", session.getCurrentStage(), session.getMsisdn());
        USSDResponse response = ussdRequestService.handleRequest(session, request);
        ussdSessionRepository.save(session);
        log.info("ussd next stage - {} phone - {}", session.getCurrentStage(), session.getMsisdn());
        return response;
    }

    private UssdSession fetchExistingSession(USSDRequest request) {
        Optional<UssdSession> optionalUssdSession = ussdSessionRepository.findById(request.getSessionId());
        return optionalUssdSession.orElseGet(() -> createNewSession(request));

    }

    private UssdSession createNewSession(USSDRequest request) {
        UssdSession session = new UssdSession();
        session.setMsisdn(request.getMsisdn());
        session.setSessionId(request.getSessionId());

        return session;
    }

    private boolean isNewCustomer(String msisdn, UssdSession session) {
        CustomerData customerData = empayItOnboardingService.validateCustomer(msisdn);

        if (!isNull(customerData)) {
            session.setData(USER_EXIST, "1");
            session.setData(CUSTOMER_ID, customerData.getUserId());
            session.setData(FIRST_NAME, customerData.getFirstName());
            session.setData(LAST_NAME, customerData.getLastName());
            if (!isNull(customerData.getDob())) {
                session.setData(CUSTOMER_DOB, customerData.getDob().toString());
            }
            return false;
        }

        return true;
    }
}