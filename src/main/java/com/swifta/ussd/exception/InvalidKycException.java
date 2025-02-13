package com.swifta.ussd.exception;

public class InvalidKycException extends RuntimeException {

    private String missingData;

    public InvalidKycException(String msisdn, String missingData) {
        super(String.format("invalid kyc for msisdn = %s, missing_date =%s", msisdn, missingData));
        this.missingData = missingData;
    }

    public String getMissingData() {
        return missingData;
    }
}
