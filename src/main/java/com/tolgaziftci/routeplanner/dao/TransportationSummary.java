package com.tolgaziftci.routeplanner.dao;

import com.tolgaziftci.routeplanner.entity.TransportationType;

public record TransportationSummary(int id, int originId, int destId, TransportationType type) {

}
