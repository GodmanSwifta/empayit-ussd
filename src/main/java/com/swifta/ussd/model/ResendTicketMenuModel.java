package com.swifta.ussd.model;

import com.swifta.ussd.dto.ResendTicketResponse;

public class ResendTicketMenuModel  implements MenuPageModel{

    private ResendTicketResponse resendTicket;

    public ResendTicketMenuModel() {
    }

    public ResendTicketMenuModel(ResendTicketResponse resendTicket) {
        this.resendTicket = resendTicket;
    }
    @SuppressWarnings("unchecked")
    @Override
    public ResendTicketResponse getObject() {
        return resendTicket;
    }

    @Override
    public String getStringValue() {
        return  "Ticket ID/" + resendTicket.getTicketId() + "/" + resendTicket.getEventName();
    }
}
