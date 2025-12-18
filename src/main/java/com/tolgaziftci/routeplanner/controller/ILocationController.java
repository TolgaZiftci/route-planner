package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import com.tolgaziftci.routeplanner.request.LocationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Locations", description = "CRUD operations involving locations")
@RequestMapping("/api/location")
public interface ILocationController {

    @Operation(
            summary = "Get a location by its ID",
            parameters = {@Parameter(name = "id", description = "Location ID")},
            tags = {"get"}
    )
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "204",
            description = "If a location with the given ID does not exist"
    )
    @GetMapping("/{id}")
    public ResponseEntity<LocationDao> findLocationById(@PathVariable int id);

    @Operation(
            summary = "Get a list of all locations",
            tags = {"get"}
    )
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true,
            description = "Can be an empty list"
    )
    @GetMapping
    public ResponseEntity<List<LocationDao>> getAllLocations();

    @Operation(
            summary = "Add a new location to the database",
            tags = {"post"}
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = """
                    name: Location name
                    
                    country: Country of location
                    
                    city: City of location
                    
                    locationCode: The code for the location (IATA airport codes are used for airports)
                    """
    )
    @ApiResponse(
            responseCode = "400",
            description = "If the location already exists or any field is empty"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Full specification of the new location including ID",
            useReturnTypeSchema = true
    )
    @PostMapping
    public ResponseEntity<LocationDao> addLocation(@RequestBody LocationRequest location);

    @Operation(
            summary = "Delete a location from the database",
            parameters = {@Parameter(name = "id", description = "ID of the location to delete")},
            tags = {"delete"}
    )
    @ApiResponse(
            responseCode = "404",
            description = "If the location does not exist"
    )
    @ApiResponse(
            responseCode = "400",
            description = "If the location has transportations attached"
    )
    @ApiResponse(
            responseCode = "200",
            description = "If the location was successfully deleted"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<LocationDao> removeLocation(@PathVariable int id);

    @Operation(
            summary = "Update a location in the database",
            tags = {"put"}
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = """
                    name: Location name
                    
                    country: Country of location
                    
                    city: City of location
                    
                    locationCode: The code for the location (IATA airport codes are used for airports)
                    """
    )
    @ApiResponse(
            responseCode = "404",
            description = "If the location does not exist"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Full specification of the updated location including ID",
            useReturnTypeSchema = true
    )
    @PutMapping("/{id}")
    public ResponseEntity<LocationDao> updateLocation(@PathVariable int id, @RequestBody LocationRequest location);
}
