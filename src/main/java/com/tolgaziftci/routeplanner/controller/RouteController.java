package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.TransportationDao;
import com.tolgaziftci.routeplanner.entity.Route;
import com.tolgaziftci.routeplanner.repository.TransportationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final TransportationRepository transportationRepository;

    public RouteController(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    @GetMapping
    public ResponseEntity<Route> findRoutes(@RequestParam int originLocation, @RequestParam int destLocation) {
        List<TransportationDao> transportations = transportationRepository.findByOriginLocation_IdAndDestLocation_Id(originLocation, destLocation);
        return new ResponseEntity<>(new Route(transportations), HttpStatus.OK);
    }
}
