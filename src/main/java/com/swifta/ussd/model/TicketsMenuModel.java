package com.swifta.ussd.model;

import com.swifta.ussd.dto.TicketBouquetData;

public class TicketsMenuModel implements MenuPageModel {

    //TODO: TICKET MODEL
    private TicketBouquetData data;

    public TicketsMenuModel() {

    }

    public TicketsMenuModel(TicketBouquetData data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TicketBouquetData getObject() {
        return data;
    }

    @Override
    public String getStringValue() {
        return data.getName();
    }
}
