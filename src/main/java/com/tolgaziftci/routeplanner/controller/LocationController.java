package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import com.tolgaziftci.routeplanner.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/location")
public class LocationController {
    private final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{name}")
    public LocationDao findLocationByName(@PathVariable String name) {
        LOGGER.info("request received for {}", name);
        return repository.findByName(name);
    }

    @GetMapping("/")
    public List<LocationDao> findLocations() {
        return repository.findAll();
    }
}
