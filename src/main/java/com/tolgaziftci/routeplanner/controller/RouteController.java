package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.TransportationDao;
import com.tolgaziftci.routeplanner.entity.Route;
import com.tolgaziftci.routeplanner.repository.TransportationRepository;
import com.tolgaziftci.routeplanner.route.FullRoute;
import com.tolgaziftci.routeplanner.route.RouteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final TransportationRepository transportationRepository;

    private final RouteService routeService;

    public RouteController(TransportationRepository transportationRepository, RouteService routeService) {
        this.transportationRepository = transportationRepository;
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<List<Route>> findRoutes(@RequestParam int originLocation, @RequestParam int destLocation,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<FullRoute> routes = routeService.getAllRoutes(originLocation, destLocation, date);
        routes.sort(Comparator.comparingInt(o -> o.getNodes().size()));
        List<Route> response = new ArrayList<>(routes.size());
        for (FullRoute route : routes) {
            List<TransportationDao> transportations = new ArrayList<>(route.getTypes().size());
            for (int i = 0; i < route.getTypes().size(); i++) {
                transportations.add(transportationRepository.findByOriginLocation_IdAndDestLocation_IdAndType(
                        route.getNodes().get(i), route.getNodes().get(i + 1), route.getTypes().get(i)).orElseThrow());
            }
            response.add(new Route(transportations));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
