package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.dao.TransportationDao;
import com.tolgaziftci.routeplanner.request.TransportationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Transportations", description = "CRUD operations involving transportations")
@RequestMapping("/api/transportation")
public interface ITransportationController {
    @Operation(
            summary = "Get a transportation by its ID",
            parameters = {@Parameter(name = "id", description = "Transportation ID")},
            tags = {"get"}
    )
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "204",
            description = "If a transportation with the given ID does not exist"
    )
    @GetMapping("/{id}")
    ResponseEntity<TransportationDao> findTransportationById(@PathVariable int id);

    @Operation(
            summary = "Get a list of all transportations",
            tags = {"get"}
    )
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true,
            description = "Can be an empty list"
    )
    @GetMapping
    ResponseEntity<List<TransportationDao>> getAllTransportations();

    @Operation(
            summary = "Add a new transportation to the database",
            tags = {"post"}
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = """
                    originLocation and destLocation: Location IDs
                    
                    type: Transportation type (can be FLIGHT, UBER, BUS, SUBWAY)
                    
                    operatingDays: Array of integers representing the days of week the transportation is active
                    """
    )
    @ApiResponse(
            responseCode = "400",
            description = "If the origin and destination locations are the same, or if the origin and destination locations does not exist, " +
                    "or the transportation already exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Full specification of the new transportation including ID",
            useReturnTypeSchema = true
    )
    @PostMapping
    ResponseEntity<TransportationDao> addTransportation(@RequestBody TransportationRequest transportation);

    @Operation(
            summary = "Delete a transportation from the database",
            parameters = {@Parameter(name = "id", description = "ID of the transportation to delete")},
            tags = {"delete"}
    )
    @ApiResponse(
            responseCode = "404",
            description = "If the transportation does not exist"
    )
    @ApiResponse(
            responseCode = "200",
            description = "If the transportation was successfully deleted"
    )
    @DeleteMapping("/{id}")
    ResponseEntity<TransportationDao> removeTransportation(@PathVariable int id);
}
