package com.swifta.ussd.dto;

import com.swifta.ussd.enums.FreeflowState;

import java.util.Objects;

public class FreeflowRequest {
    public static final FreeflowRequest FC = new FreeflowRequest(FreeflowState.FC);
    public static final FreeflowRequest FB = new FreeflowRequest(FreeflowState.FB);
    private FreeflowState mode;

    public FreeflowRequest() {
    }

    public FreeflowRequest(FreeflowState mode) {
        this.mode = mode;
    }

    public FreeflowState getMode() {
        return mode;
    }

    public void setMode(FreeflowState mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FreeflowRequest freeflow = (FreeflowRequest) o;
        return mode == freeflow.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode);
    }

    @Override
    public String toString() {
        return "Freeflow{" +
                "freeflowState=" + mode +
                '}';
    }
}
