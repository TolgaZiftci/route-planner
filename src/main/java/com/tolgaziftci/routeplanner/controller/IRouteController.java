package com.tolgaziftci.routeplanner.controller;

import com.tolgaziftci.routeplanner.entity.Route;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Routes", description = "Endpoint for planning routes")
@RequestMapping("/api/route")
public interface IRouteController {
    @Operation(
            summary = "Calculate the routes between the origin and destination locations",
            parameters = {
                    @Parameter(name = "originLocation", description = "Starting location"),
                    @Parameter(name = "destLocation", description = "Destination"),
                    @Parameter(name = "date", description = "Date to arrange route details at (YYYY-MM-DD)")
            },
            tags = {"get"}
    )
    @ApiResponse(
            responseCode = "400",
            description = "If the origin or destination location does not exist"
    )
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @GetMapping
    ResponseEntity<List<Route>> findRoutes(@RequestParam int originLocation,
                                           @RequestParam int destLocation,
                                           @RequestParam
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}
