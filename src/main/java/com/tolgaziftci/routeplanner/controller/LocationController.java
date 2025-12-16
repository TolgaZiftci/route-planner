package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import com.tolgaziftci.routeplanner.entity.Location;
import com.tolgaziftci.routeplanner.repository.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationController implements ILocationController {

    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<LocationDao> findLocationById(@PathVariable int id) {
        Optional<LocationDao> response = repository.findById(id);
        return response.map(locationDao -> new ResponseEntity<>(locationDao, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    public ResponseEntity<List<LocationDao>> getAllLocations() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<LocationDao> addLocation(@RequestBody Location location) {
        return new ResponseEntity<>(repository.save(new LocationDao(location)), HttpStatus.OK);
    }

    public ResponseEntity<LocationDao> removeLocation(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
