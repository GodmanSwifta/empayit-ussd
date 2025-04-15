package com.swifta.ussd.service.screens;

import com.swifta.ussd.constant.AppMessages;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.entity.cache.UssdSession;
import com.swifta.ussd.service.StageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.swifta.ussd.constant.AppMessages.DOB_MESSAGE;
import static com.swifta.ussd.constant.PropertyKeys.CUSTOMER_DOB;
import static com.swifta.ussd.constant.PropertyKeys.DOB_RETRY;
import static com.swifta.ussd.constant.Stage.*;

@Slf4j
@Component
public class DobStageHandler implements StageHandler {
    @Override
    public void processStage(UssdSession session) {
        String input = session.getUssdInput();
        session.setCurrentStage(getStageOption(input, session));
    }

    private String getStageOption(String input, UssdSession session) {
        if(isDobValid(input,session)) {
            session.setData(DOB_RETRY, "false");
            return KYC_CONFIRMATION;
        } else {
            session.setData(DOB_RETRY, "true");
            return DOB;
        }
    }

    private boolean isDobValid(String input, UssdSession session) {
        String userDob;
        try {
            input = input.replaceAll("/|-|\\s", "");

            if (input.length() != 8 || !input.matches("\\d+")) {
                return false;
            }

            DateFormat df = new SimpleDateFormat("ddMMyyyy");
            Date date = df.parse(input);

            df = new SimpleDateFormat("yyyy-MM-dd");
            userDob = df.format(date);

            LocalDate today = LocalDate.now();

            if (Period.between(LocalDate.parse(userDob), today).getYears() < 18) {
                return false;
            }

            String storedDob = session.getData(CUSTOMER_DOB);

            if (storedDob != null && !storedDob.isEmpty()) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate storedDobDate = LocalDate.parse(storedDob, dtf);
                LocalDate inputDobDate = LocalDate.parse(userDob);

                if (!storedDobDate.equals(inputDobDate)) {
                    return false;
                }
            }
            session.setData(CUSTOMER_DOB, input);


        } catch (ParseException e) {
            log.error("Error Parsing DOB", e);
            return false;
        }
        return true;
    }

    @Override
    public String getStage() {
        return DOB;
    }

    @Override
    public USSDResponse loadPage(UssdSession session) {
        String message = session.getData(DOB_RETRY).equalsIgnoreCase("true") ? AppMessages.DOB_RETRY_MESSAGE : DOB_MESSAGE;
        return USSDResponse.builder()
                .msisdn(session.getMsisdn())
                .applicationResponse(message)
                .freeflow(Freeflow.FC)
                .build();
    }
}
