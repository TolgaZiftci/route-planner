package com.tolgaziftci.routeplanner.dto;

import com.tolgaziftci.routeplanner.entity.TransportationType;

public class TransportationDto {
    private int originLocation;
    private int destLocation;
    private TransportationType type;

    public TransportationDto() {
    }

    public TransportationDto(int originLocation, int destLocation, TransportationType type) {
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.type = type;
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
}
