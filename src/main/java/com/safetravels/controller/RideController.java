package com.safetravels.controller;

import com.safetravels.dto.request.CreateRideRequest;
import com.safetravels.dto.response.RideResponse;
import com.safetravels.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideResponse> createRide(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateRideRequest request) {
        RideResponse response = rideService.createRide(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<RideResponse> startRide(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        RideResponse response = rideService.startRide(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<RideResponse> endRide(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        RideResponse response = rideService.endRide(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRide(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        RideResponse response = rideService.getRide(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RideResponse>> getUserRides(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<RideResponse> rides = rideService.getUserRides(userDetails.getUsername());
        return ResponseEntity.ok(rides);
    }
}
