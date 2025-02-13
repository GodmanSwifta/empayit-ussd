package com.swifta.ussd.dto.simreg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimRegInfo {
    private String title;
    private String familyName;
    private String firstName;
    private String gender;
    private String dateOfBirth;
    private String personDetails;
    private String nationality;
    private List<String> completeNumber;//should be phone Number
    private List<String> completeEmail;
    private List<AddressDetails> addressDetails;

    public boolean isValid() {
        return StringUtils.isNotEmpty(familyName) && StringUtils.isNotEmpty(firstName) && checkAddressDetails();
    }

    private boolean checkAddressDetails() {
        return addressDetails != null && !addressDetails.isEmpty() &&
                StringUtils.isNotEmpty(addressDetails.getFirst().toFormattedString());
    }

    @Override
    public String toString() {
        return "SImRegInfo{" +
                "title='" + title + '\'' +
                ", familyName='" + familyName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", personDetails='" + personDetails + '\'' +
                ", nationality='" + nationality + '\'' +
                ", completeNumber=" + completeNumber +
                ", completeEmail=" + completeEmail +
                ", addressDetails=" + addressDetails.toString() +
                '}';
    }
}
