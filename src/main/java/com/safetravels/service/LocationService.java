package com.safetravels.service;

import com.safetravels.dto.request.UpdateLocationRequest;
import com.safetravels.dto.response.LocationResponse;

public interface LocationService {
    LocationResponse updateLocation(String ownerEmail, Long rideId, UpdateLocationRequest request);
}
