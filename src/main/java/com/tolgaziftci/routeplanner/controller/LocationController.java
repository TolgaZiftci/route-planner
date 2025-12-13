package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import com.tolgaziftci.routeplanner.entity.Location;
import com.tolgaziftci.routeplanner.repository.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/location")
public class LocationController {

    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDao> findLocationById(@PathVariable int id) {
        Optional<LocationDao> response = repository.findById(id);
        return response.map(locationDao -> new ResponseEntity<>(locationDao, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping()
    public ResponseEntity<List<LocationDao>> getAllLocations() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<LocationDao> addLocation(@RequestBody Location location) {
        return new ResponseEntity<>(repository.save(new LocationDao(location)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationDao> removeLocation(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
