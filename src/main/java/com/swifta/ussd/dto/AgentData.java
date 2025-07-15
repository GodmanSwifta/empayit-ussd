package com.swifta.ussd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
public class AgentData {

    private long id;

    private String userId;

    private String phone;
    protected String firstName;
    protected String lastName;
    private boolean active;


}
