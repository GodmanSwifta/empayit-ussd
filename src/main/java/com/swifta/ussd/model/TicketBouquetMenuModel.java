package com.swifta.ussd.model;

import com.swifta.ussd.dto.TicketBouquetData;

public class TicketBouquetMenuModel implements MenuPageModel {

    private TicketBouquetData data;

    public TicketBouquetMenuModel() {

    }

    public TicketBouquetMenuModel(TicketBouquetData data) {
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
