package com.safetravels.service.impl;

import com.safetravels.dto.request.CreateRideRequest;
import com.safetravels.dto.response.RideResponse;
import com.safetravels.entity.Ride;
import com.safetravels.entity.User;
import com.safetravels.enums.RideStatus;
import com.safetravels.exception.ResourceNotFoundException;
import com.safetravels.exception.UnauthorizedException;
import com.safetravels.exception.ValidationException;
import com.safetravels.mapper.RideMapper;
import com.safetravels.repository.RideRepository;
import com.safetravels.service.RideService;
import com.safetravels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserService userService;
    private final RideMapper rideMapper;

    @Override
    @Transactional
    public RideResponse createRide(String ownerEmail, CreateRideRequest request) {
        User owner = userService.findUserByEmail(ownerEmail);

        Ride ride = Ride.builder()
                .owner(owner)
                .startAddress(request.getStartAddress())
                .destinationAddress(request.getDestinationAddress())
                .driverName(request.getDriverName())
                .driverPlate(request.getDriverPlate())
                .status(RideStatus.CREATED)
                .build();

        ride = rideRepository.save(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse startRide(String ownerEmail, Long rideId) {
        Ride ride = findRideAndCheckOwnership(ownerEmail, rideId);

        if (ride.getStatus() != RideStatus.CREATED) {
            throw new ValidationException("Ride is not in CREATED status");
        }

        ride.setStatus(RideStatus.ONGOING);
        ride.setStartTime(LocalDateTime.now());
        ride = rideRepository.save(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse endRide(String ownerEmail, Long rideId) {
        Ride ride = findRideAndCheckOwnership(ownerEmail, rideId);

        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new ValidationException("Ride is not in ONGOING status");
        }

        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        ride = rideRepository.save(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    public RideResponse getRide(String ownerEmail, Long rideId) {
        Ride ride = findRideAndCheckOwnership(ownerEmail, rideId);
        return rideMapper.toResponse(ride);
    }

    @Override
    public List<RideResponse> getUserRides(String ownerEmail) {
        User owner = userService.findUserByEmail(ownerEmail);
        return rideRepository.findByOwnerOrderByCreatedAtDesc(owner).stream()
                .map(rideMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Ride findRideAndCheckOwnership(String ownerEmail, Long rideId) {
        User owner = userService.findUserByEmail(ownerEmail);
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!ride.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedException("You can only access your own rides");
        }

        return ride;
    }
}
