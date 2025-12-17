package com.tolgaziftci.routeplanner.dao;

import com.tolgaziftci.routeplanner.entity.TransportationType;

import java.util.Arrays;
import java.util.Objects;

public class TransportationSummary {
    private final int id;
    private final int originId;
    private final int destId;
    private final TransportationType type;
    private final int[] operatingDays;

    public TransportationSummary(int id, int originId, int destId, TransportationType type, int[] operatingDays) {
        this.id = id;
        this.originId = originId;
        this.destId = destId;
        this.type = type;
        this.operatingDays = operatingDays;
    }

    public int getId() {
        return id;
    }

    public int getOriginId() {
        return originId;
    }

    public int getDestId() {
        return destId;
    }

    public TransportationType getType() {
        return type;
    }

    public int[] getOperatingDays() {
        return operatingDays;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransportationSummary that = (TransportationSummary) o;
        return id == that.id && originId == that.originId && destId == that.destId && type == that.type && Objects.deepEquals(operatingDays, that.operatingDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originId, destId, type, Arrays.hashCode(operatingDays));
    }
}
