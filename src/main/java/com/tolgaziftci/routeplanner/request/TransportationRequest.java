package com.tolgaziftci.routeplanner.request;

import com.tolgaziftci.routeplanner.entity.TransportationType;

public class TransportationRequest {
    private int originLocation;
    private int destLocation;
    private TransportationType type;
    private int[] operatingDays;

    public TransportationRequest() {
    }

    public TransportationRequest(int originLocation, int destLocation, TransportationType type, int[] operatingDays) {
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.type = type;
        this.operatingDays = operatingDays;
    }

    public int getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(int originLocation) {
        this.originLocation = originLocation;
    }

    public int getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(int destLocation) {
        this.destLocation = destLocation;
    }

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public int[] getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(int[] operatingDays) {
        this.operatingDays = operatingDays;
    }
}
