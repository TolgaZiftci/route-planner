package com.tolgaziftci.routeplanner.route;

import com.tolgaziftci.routeplanner.dao.TransportationSummary;
import com.tolgaziftci.routeplanner.entity.TransportationType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteFinder {

    private final Map<Integer, List<GraphEdge>> graph;
    private final int originLocation;
    private final int destLocation;

    public RouteFinder(int originLocation, int destLocation) {
        this.graph = new HashMap<>();
        this.originLocation = originLocation;
        this.destLocation = destLocation;
    }

    /**
     * Initialize the route graph for traversal
     */
    public void init(List<TransportationSummary> transportations) {
        for (TransportationSummary transportation : transportations) {
            graph.computeIfAbsent(transportation.getOriginId(), k -> new ArrayList<>()).add(
                    new GraphEdge(transportation.getDestId(), transportation.getType()));
        }
    }

    /**
     * Use iterative DFS to find all routes to the destination
     * @return list of routes
     */
    public List<FullRoute> findRoutes() {
        List<FullRoute> result = new ArrayList<>();
        Deque<State> stack = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        stack.push(new State(originLocation, null, 0, 0, null));

        // Main DFS loop
        while (!stack.isEmpty()) {
            State state = stack.pop();
            int node = state.node;

            // rebuild visited set for this branch
            // used to avoid cycles in the route
            visited.clear();
            for (State s = state; s != null; s = s.parent) {
                visited.add(s.node);
            }

            // reached destination, accept only if there is exactly one FLIGHT in the route
            if (node == destLocation && state.flightCount == 1) {
                result.add(reconstructFullPath(state));
                continue;
            }

            // stop checking further if we reach 3 transportations
            if (state.depth == 3) {
                continue;
            }

            List<GraphEdge> neighbors = graph.get(node);
            if (neighbors == null) continue;

            for (GraphEdge edge : neighbors) {
                if (visited.contains(edge.toLocation)) {
                    // do not create a cycle
                    continue;
                }

                // compute new flight count in the route
                int newFlightCount = state.flightCount + (edge.type == TransportationType.FLIGHT ? 1 : 0);

                // route cannot have more than one FLIGHT
                if (newFlightCount > 1) {
                    continue;
                }

                // two consecutive transportations cannot be FLIGHTs
                if (state.lastEdgeType != null && state.lastEdgeType != TransportationType.FLIGHT &&
                        edge.type != TransportationType.FLIGHT) {
                    continue;
                }

                stack.push(new State(edge.toLocation, state, state.depth + 1, newFlightCount, edge.type));
            }
        }

        return result;
    }

    private static FullRoute reconstructFullPath(State state) {
        LinkedList<Integer> nodes = new LinkedList<>();
        LinkedList<TransportationType> types = new LinkedList<>();

        State cur = state;
        while (cur != null) {
            nodes.addFirst(cur.node);
            if (cur.lastEdgeType != null) {
                types.addFirst(cur.lastEdgeType);
            }
            cur = cur.parent;
        }

        return new FullRoute(nodes, types);
    }

    /**
     * Internal state for the traversal algorithm
     */
    private static class State {
        int node;
        State parent;
        // current route length
        int depth;
        // number of flights in the route
        int flightCount;
        // the type of the last transportation in the path (initial null)
        TransportationType lastEdgeType;

        State(int node, State parent, int depth, int flightCount, TransportationType lastEdgeType) {
            this.node = node;
            this.parent = parent;
            this.depth = depth;
            this.flightCount = flightCount;
            this.lastEdgeType = lastEdgeType;
        }
    }

    private static class GraphEdge {
        int toLocation;
        TransportationType type;

        public GraphEdge(int toLocation, TransportationType type) {
            this.toLocation = toLocation;
            this.type = type;
        }
    }
}
