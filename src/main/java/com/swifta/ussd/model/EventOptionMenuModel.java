package com.swifta.ussd.model;

import com.swifta.ussd.dto.EventOptionData;

public class EventOptionMenuModel implements MenuPageModel{
    private EventOptionData eventOption;

    public EventOptionMenuModel(EventOptionData eventOption) {
        this.eventOption = eventOption;
    }

    public EventOptionMenuModel() {
    }


    @SuppressWarnings("unchecked")
    @Override
    public EventOptionData getObject() {
        return eventOption;
    }

    @Override
    public String getStringValue() {
        return eventOption.getOptionName();
    }
}
