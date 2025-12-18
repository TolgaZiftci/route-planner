package com.tolgaziftci.routeplanner;

import com.tolgaziftci.routeplanner.dao.TransportationSummary;
import com.tolgaziftci.routeplanner.entity.TransportationType;
import com.tolgaziftci.routeplanner.route.RouteFinder;
import com.tolgaziftci.routeplanner.route.RouteSummary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRouteFinder {

    private Set<TransportationSummary> transportations;

    /**
     * Transportations:
     * <ul>
     * <li> 1 -> 2 (BUS) [1, 2] </li>
     * <li> 1 -> 2 (UBER) [2, 3] </li>
     * <li> 2 -> 3 (FLIGHT) [1, 2, 3] </li>
     * <li> 3 -> 4 (SUBWAY) [2, 3] </li>
     * </ul>
     *
     * Tests can add or remove transportations as necessary
     */
    @BeforeEach
    public void setUp() {
        transportations = new HashSet<>();
        transportations.add(new TransportationSummary(1, 1, 2, TransportationType.BUS, new int[]{1, 2}));
        transportations.add(new TransportationSummary(2, 1, 2, TransportationType.UBER, new int[]{2, 3}));
        transportations.add(new TransportationSummary(3, 2, 3, TransportationType.FLIGHT, new int[]{1, 2, 3}));
        transportations.add(new TransportationSummary(4, 3, 4, TransportationType.SUBWAY, new int[]{2, 3}));
    }

    @Test
    public void testRouteExists() {
        List<RouteSummary> routes = getRoutes(1, 4);
        assertEquals(2, routes.size());
        for (RouteSummary route : routes) {
            assertEquals(TransportationType.FLIGHT, route.getTypes().get(1));
            assertEquals(4, route.getNodes().get(route.getNodes().size() - 1));
        }
    }

    @Test
    public void testRouteHasNoFlight() {
        List<RouteSummary> routes = getRoutes(1, 2);
        assertEquals(0, routes.size());
    }

    @Test
    public void testRouteWithOperatingDays() {
        // assume we travel at Wednesday (3), delete first transport as it does not operate on Wednesdays
        transportations.remove(new TransportationSummary(1, 1, 2, TransportationType.BUS, new int[]{1, 2}));
        List<RouteSummary> routes = getRoutes(1, 4);
        assertEquals(1, routes.size());
        for (RouteSummary route : routes) {
            assertEquals(TransportationType.FLIGHT, route.getTypes().get(1));
            assertEquals(4, route.getNodes().get(route.getNodes().size() - 1));
        }
    }

    @Test
    public void testRouteHasTwoConsecutiveNonFlights() {
        // new route: 5 -> 1 (SUBWAY) [1, 2]
        transportations.add(new TransportationSummary(5, 5, 1, TransportationType.SUBWAY, new int[]{1, 2}));
        List<RouteSummary> routes = getRoutes(5, 3);
        assertEquals(0, routes.size());
    }

    @Test
    public void testRouteWithSingleTransportation() {
        List<RouteSummary> routes = getRoutes(2, 3);
        assertEquals(1, routes.size());
        assertEquals(TransportationType.FLIGHT, routes.get(0).getTypes().get(0));
        assertEquals(3, routes.get(0).getNodes().get(routes.get(0).getNodes().size() - 1));
        assertEquals(1, routes.get(0).getTypes().size());
    }

    private List<RouteSummary> getRoutes(int originLocation, int destLocation) {
        RouteFinder routeFinder = new RouteFinder(originLocation, destLocation);
        routeFinder.init(transportations);
        return routeFinder.findRoutes();
    }
}
