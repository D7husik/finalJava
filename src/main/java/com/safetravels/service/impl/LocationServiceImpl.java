package com.safetravels.service.impl;

import com.safetravels.dto.request.UpdateLocationRequest;
import com.safetravels.dto.response.LocationResponse;
import com.safetravels.entity.Location;
import com.safetravels.entity.Ride;
import com.safetravels.entity.User;
import com.safetravels.enums.RideStatus;
import com.safetravels.exception.ResourceNotFoundException;
import com.safetravels.exception.UnauthorizedException;
import com.safetravels.exception.ValidationException;
import com.safetravels.mapper.LocationMapper;
import com.safetravels.repository.LocationRepository;
import com.safetravels.repository.RideRepository;
import com.safetravels.service.LocationService;
import com.safetravels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final RideRepository rideRepository;
    private final UserService userService;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public LocationResponse updateLocation(String ownerEmail, Long rideId, UpdateLocationRequest request) {
        User owner = userService.findUserByEmail(ownerEmail);
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!ride.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedException("You can only update location for your own rides");
        }

        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new ValidationException("Location can only be updated for ongoing rides");
        }

        Location location = Location.builder()
                .ride(ride)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        location = locationRepository.save(location);
        return locationMapper.toResponse(location);
    }
}
