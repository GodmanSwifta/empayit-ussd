package com.swifta.ussd.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {
    @JsonProperty("id")
    private long id;
    @JsonProperty
    private LocalDate dob;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("user_Id")
    private String userId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;




}
