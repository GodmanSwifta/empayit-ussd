package com.swifta.ussd.dto.simreg;

import lombok.Data;

import java.util.Objects;

@Data
public class AddressDetails {
    private String useCode;
    private String lineOne;
    private String lineTwo;
    private Object lineThree;
    private String cityName;
    private String district;
    private String state;
    private String countryName;
    private String countryCode;
    private String cityCode;
    private String postalCode;

    public String toFormattedString(){
        return checkDetail(useCode).concat(checkDetail(lineOne)).concat(checkDetail(lineTwo))
                .concat(checkDetail(String.valueOf(lineThree))).concat(checkDetail(cityName))
                .concat(checkDetail(String.valueOf(district))).concat(checkDetail(state))
                .concat(checkDetail(String.valueOf(countryName))).concat(checkDetail(countryCode))
                .concat(checkDetail(String.valueOf(cityCode))).concat(checkDetail(postalCode));
    }

    private String checkDetail(String s){
       return !Objects.isNull(s) ? s.concat(", ") : "";
    }
}
