package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import com.tolgaziftci.routeplanner.request.LocationRequest;
import com.tolgaziftci.routeplanner.repository.LocationRepository;
import org.springframework.dao.DataIntegrityViolationException;
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

    public ResponseEntity<LocationDao> addLocation(@RequestBody LocationRequest request) {
        if (repository.existsByNameAndCityAndCountryAndLocationCode(
                request.name(), request.city(), request.country(), request.locationCode())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (request.name().isEmpty() || request.country().isEmpty() || request.city().isEmpty() || request.locationCode().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LocationDao newObject = new LocationDao(0, request.name(), request.country(), request.city(), request.locationCode());
        return new ResponseEntity<>(repository.save(newObject), HttpStatus.OK);
    }

    public ResponseEntity<LocationDao> removeLocation(@PathVariable int id) {
        if (repository.existsById(id)) {
            try {
                repository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<LocationDao> updateLocation(@PathVariable int id, @RequestBody LocationRequest location) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (location.name().isEmpty() || location.country().isEmpty() || location.city().isEmpty() || location.locationCode().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByNameAndCityAndCountryAndLocationCode(location.name(), location.city(), location.country(), location.locationCode())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LocationDao locationToUpdate = repository.findById(id).get();
        locationToUpdate.setName(location.name());
        locationToUpdate.setCountry(location.country());
        locationToUpdate.setCity(location.city());
        locationToUpdate.setLocationCode(location.locationCode());
        LocationDao newLocation = repository.save(locationToUpdate);
        return new ResponseEntity<>(newLocation, HttpStatus.OK);
    }
}
