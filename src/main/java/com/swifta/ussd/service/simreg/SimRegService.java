package com.swifta.ussd.service.simreg;

import com.swifta.ussd.dto.simreg.AddressDetails;
import com.swifta.ussd.dto.simreg.SimRegInfo;
import com.swifta.ussd.exception.InvalidKycException;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class SimRegService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${core.simReg.url}")
    private String simRegUrl;

    private final RestOperations restOperations;


    private String resolveMissingData(SimRegInfo simRegInfo) {
        List<String> fields = new ArrayList<>();
        if (isNull(simRegInfo.getFamilyName()) || isNull(simRegInfo.getFirstName()) ||
                simRegInfo.getFamilyName().isEmpty() || simRegInfo.getFirstName().isEmpty()) {
            fields.add("name");
        }
        if (isNull(simRegInfo.getDateOfBirth()) ||
                simRegInfo.getDateOfBirth().isEmpty()) {
            fields.add("date of birth");
        }
        List<AddressDetails> addressDetailsList = simRegInfo.getAddressDetails();
        AddressDetails addressDetails = addressDetailsList.get(0);
        if (isNull(addressDetails) ||
                String.valueOf(addressDetails).isEmpty()) {
            fields.add("Address");
        }
        return fields.size() == 1 ? fields.get(0) : fields.size() == 2 ? fields.get(0).concat(" and " + fields.get(1)) :
                fields.get(0).concat(", " + fields.get(1) + " and " + fields.get(2));
    }

    @Timed
    @Transactional
    public SimRegInfo getSimRegInfo(String msisdn) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<SimRegInfo> responseEntity = restOperations.exchange(MessageFormat.format(simRegUrl, msisdn),
                HttpMethod.GET, request, SimRegInfo.class
        );

        SimRegInfo simRegInfo = responseEntity.getBody();

        logger.info(msisdn + "SIMRegInfo to Customer -> {}", simRegInfo.toString());

        if (!simRegInfo.isValid()) {
            String missingData = resolveMissingData(simRegInfo);
            throw new InvalidKycException(msisdn, missingData);
        }

        return simRegInfo;
    }

}
