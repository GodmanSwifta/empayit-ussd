package com.swifta.ussd.dto;


import com.swifta.ussd.enums.FreeflowState;

import java.util.Objects;

public class Freeflow {
    public static final Freeflow FC = new Freeflow(FreeflowState.FC);
    public static final Freeflow FB = new Freeflow(FreeflowState.FB);
    private FreeflowState freeflowState;

    public Freeflow() {
    }

    public Freeflow(FreeflowState freeflowState) {
        this.freeflowState = freeflowState;
    }

    public FreeflowState getFreeflowState() {
        return freeflowState;
    }

    public void setFreeflowState(FreeflowState freeflowState) {
        this.freeflowState = freeflowState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freeflow freeflow = (Freeflow) o;
        return freeflowState == freeflow.freeflowState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(freeflowState);
    }

    @Override
    public String toString() {
        return "Freeflow{" +
                "freeflowState=" + freeflowState +
                '}';
    }
}
