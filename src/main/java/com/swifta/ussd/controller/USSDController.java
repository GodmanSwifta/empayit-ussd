package com.swifta.ussd.controller;

import com.swifta.ussd.constant.AppMessages;
import com.swifta.ussd.dto.Freeflow;
import com.swifta.ussd.dto.USSDRequest;
import com.swifta.ussd.dto.USSDResponse;
import com.swifta.ussd.service.UssdService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/ussd/api")
@Timed
@RequiredArgsConstructor
public class USSDController {

    private static final String DEFAULT_ERROR_MESSAGE = "Service not available at the moment";

    private final UssdService ussdService;

    @Timed(percentiles = {0.5, 0.95, 0.999}, histogram = true)
    @PostMapping(value = "/v1/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public USSDResponse processRequest(@RequestBody @Valid USSDRequest request) {
        log.info(request.getMsisdn() + " sends USSDRequest -> {}", request);
        USSDResponse ussdResponse = getUssdResponse(request);

        log.info(request.getMsisdn() + " USSDResponse -> {}", ussdResponse);

        return ussdResponse;
    }

    private USSDResponse getUssdResponse(USSDRequest request) {

        USSDResponse ussdResponse;
        try {
            ussdResponse = ussdService.processRequest(request);
        } catch (NumberFormatException e) {
            ussdResponse = new USSDResponse();
            ussdResponse.setMsisdn(request.getMsisdn());
            ussdResponse.setApplicationResponse("Invalid input");
            ussdResponse.setFreeflow(Freeflow.FB);

            log.info(request.getMsisdn() + " NumberFormatException");

        } catch (IndexOutOfBoundsException e) {
            ussdResponse = failureResponse(request.getMsisdn(), AppMessages.INVALID_INPUT);
            log.error("{} has IndexOutOfBoundsException {}", request.getMsisdn(), e.getLocalizedMessage());
        } catch (Exception ex) { // any kind of exception
            ussdResponse = failureResponse(request.getMsisdn(), DEFAULT_ERROR_MESSAGE);

            log.error(request.getMsisdn() + " got an error " + ex.getMessage(), ex);
        }
        return ussdResponse;
    }

    private USSDResponse failureResponse(String msisdn, String responseMsg) {
        USSDResponse ussdResponse = new USSDResponse();
        ussdResponse.setMsisdn(msisdn);
        ussdResponse.setApplicationResponse(responseMsg);
        ussdResponse.setFreeflow(Freeflow.FB);
        return ussdResponse;
    }
}
