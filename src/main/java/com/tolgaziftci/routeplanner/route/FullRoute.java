package com.tolgaziftci.routeplanner.route;

import com.tolgaziftci.routeplanner.entity.TransportationType;

import java.util.List;

public class FullRoute {
    private final List<Integer> nodes;
    private final List<TransportationType> types;

    public FullRoute(List<Integer> nodes, List<TransportationType> types) {
        this.nodes = nodes;
        this.types = types;
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public List<TransportationType> getTypes() {
        return types;
    }
}
