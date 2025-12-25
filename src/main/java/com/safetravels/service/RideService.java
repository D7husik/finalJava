package com.safetravels.service;

import com.safetravels.dto.request.CreateRideRequest;
import com.safetravels.dto.response.RideResponse;

import java.util.List;

public interface RideService {
    RideResponse createRide(String ownerEmail, CreateRideRequest request);

    RideResponse startRide(String ownerEmail, Long rideId);

    RideResponse endRide(String ownerEmail, Long rideId);

    RideResponse getRide(String ownerEmail, Long rideId);

    List<RideResponse> getUserRides(String ownerEmail);
}
