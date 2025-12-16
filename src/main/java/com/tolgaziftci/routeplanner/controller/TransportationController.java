package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import com.tolgaziftci.routeplanner.dao.TransportationDao;
import com.tolgaziftci.routeplanner.request.TransportationRequest;
import com.tolgaziftci.routeplanner.repository.LocationRepository;
import com.tolgaziftci.routeplanner.repository.TransportationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TransportationController implements ITransportationController {

    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public TransportationController(TransportationRepository transportationRepository, LocationRepository locationRepository) {
        this.transportationRepository = transportationRepository;
        this.locationRepository = locationRepository;
    }

    public ResponseEntity<TransportationDao> findTransportationById(@PathVariable int id) {
        Optional<TransportationDao> response = transportationRepository.findById(id);
        return response.map(locationDao -> new ResponseEntity<>(locationDao, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    public ResponseEntity<List<TransportationDao>> getAllTransportations() {
        return new ResponseEntity<>(transportationRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<TransportationDao> addTransportation(@RequestBody TransportationRequest transportation) {
        Optional<LocationDao> originLocation = locationRepository.findById(transportation.getOriginLocation());
        Optional<LocationDao> destLocation = locationRepository.findById(transportation.getDestLocation());
        if (originLocation.isEmpty() || destLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (transportationRepository.existsByOriginLocation_IdAndDestLocation_IdAndType(
                transportation.getOriginLocation(), transportation.getDestLocation(), transportation.getType())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TransportationDao newObject =
                new TransportationDao(0, originLocation.get(), destLocation.get(), transportation.getType(), transportation.getOperatingDays());
        return new ResponseEntity<>(transportationRepository.save(newObject), HttpStatus.OK);
    }

    public ResponseEntity<TransportationDao> removeTransportation(@PathVariable int id) {
        if (transportationRepository.existsById(id)) {
            transportationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
