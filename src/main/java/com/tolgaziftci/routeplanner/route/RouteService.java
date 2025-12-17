package com.tolgaziftci.routeplanner.route;

import com.tolgaziftci.routeplanner.dao.TransportationSummary;
import com.tolgaziftci.routeplanner.repository.TransportationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RouteService {

    private final TransportationRepository transportationRepository;
    
    public RouteService(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    /**
     * Compute the list of all routes between the origin and destination locations
     * <p/>
     * There are several constraints on valid routes:
     * <ul>
     *     <li>A route cannot have more than three transportations</li>
     *     <li>A route must contain one and only one flight</li>
     *     <li>There cannot be more than one before- or after-flight transfers</li>
     * </ul>
     * @param originLocation ID of origin location
     * @param destLocation ID of destination location
     * @return list of routes fitting the constraints
     */
    public List<RouteSummary> getAllRoutes(int originLocation, int destLocation, LocalDate date) {
        RouteFinder finder = new RouteFinder(originLocation, destLocation);
        finder.init(getPathGraph(originLocation, destLocation, date.getDayOfWeek().getValue()));
        return finder.findRoutes();
    }

    private List<TransportationSummary> getPathGraph(int originLocation, int destLocation, int dayOfWeek) {
        // Get all nodes that we can reach directly
        Set<TransportationSummary> firstPaths = new HashSet<>(transportationRepository.findKeysByOriginLocation_Id(originLocation));

        // Get all nodes that can be reached from the first set of nodes
        Set<TransportationSummary> secondPaths = firstPaths.stream().map(initialLocation ->
                transportationRepository.findKeysByOriginLocation_Id(initialLocation.getDestId())).flatMap(List::stream).collect(Collectors.toSet());

        // Get all nodes that can be reached from the second set of nodes
        // The route cannot be longer than three nodes, so here we can filter by destination location
        Set<TransportationSummary> thirdPaths = secondPaths.stream().map(initialLocation ->
                transportationRepository.findKeysByOriginLocation_IdAndDestLocation_Id(initialLocation.getDestId(), destLocation)).flatMap(List::stream).collect(Collectors.toSet());

        return Stream.of(firstPaths, secondPaths, thirdPaths).flatMap(Set::stream)
                .filter(transport -> {
                    for (int operatingDay : transport.getOperatingDays()) {
                        if (operatingDay == dayOfWeek) return true;
                    }
                    return false;
                }).toList();
    }
}
