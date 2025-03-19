package com.swifta.ussd.model;

import com.swifta.ussd.dto.EventTypeData;

public class EventTypeMenuModel implements MenuPageModel {

    private EventTypeData eventType;

    public EventTypeMenuModel() {

    }

    public EventTypeMenuModel(EventTypeData eventType) {
        this.eventType = eventType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventTypeData getObject() {
        return eventType;
    }

    @Override
    public String getStringValue() {
        return eventType.getEventName();
    }
}
