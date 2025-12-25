package com.safetravels.service.impl;

import com.safetravels.dto.request.CreateSosRequest;
import com.safetravels.dto.response.SosEventResponse;
import com.safetravels.entity.Ride;
import com.safetravels.entity.SosEvent;
import com.safetravels.entity.User;
import com.safetravels.exception.ResourceNotFoundException;
import com.safetravels.exception.UnauthorizedException;
import com.safetravels.mapper.SosEventMapper;
import com.safetravels.patterns.builder.SosAlertBuilder;
import com.safetravels.patterns.observer.SosEventListener;
import com.safetravels.repository.RideRepository;
import com.safetravels.repository.SosEventRepository;
import com.safetravels.service.SosService;
import com.safetravels.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SosServiceImpl implements SosService {

    private final SosEventRepository sosEventRepository;
    private final RideRepository rideRepository;
    private final UserService userService;
    private final SosEventMapper sosEventMapper;
    private final List<SosEventListener> sosEventListeners;

    @Override
    @Transactional
    public SosEventResponse triggerSos(String userEmail, CreateSosRequest request) {
        User user = userService.findUserByEmail(userEmail);
        Ride ride = null;

        if (request.getRideId() != null) {
            ride = rideRepository.findById(request.getRideId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

            if (!ride.getOwner().getId().equals(user.getId())) {
                throw new UnauthorizedException("You can only trigger SOS for your own rides");
            }
        }

        // Using Builder Pattern
        SosEvent sosEvent = new SosAlertBuilder()
                .setTriggeredBy(user)
                .setRide(ride)
                .setMessage(request.getMessage())
                .setLatitude(request.getLatitude())
                .setLongitude(request.getLongitude())
                .build();

        sosEvent = sosEventRepository.save(sosEvent);
        log.info("SOS Event triggered by user: {} with ID: {}", userEmail, sosEvent.getId());

        // Observer Pattern: Notify all listeners
        for (SosEventListener listener : sosEventListeners) {
            listener.onSosTriggered(sosEvent);
        }

        return sosEventMapper.toResponse(sosEvent);
    }

    @Override
    public List<SosEventResponse> getUserSosEvents(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        return sosEventRepository.findByTriggeredByOrderByCreatedAtDesc(user).stream()
                .map(sosEventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SosEventResponse getSosEvent(String userEmail, Long sosId) {
        User user = userService.findUserByEmail(userEmail);
        SosEvent sosEvent = sosEventRepository.findById(sosId)
                .orElseThrow(() -> new ResourceNotFoundException("SOS event not found"));

        if (!sosEvent.getTriggeredBy().getId().equals(user.getId())) {
            throw new UnauthorizedException("You can only access your own SOS events");
        }

        return sosEventMapper.toResponse(sosEvent);
    }
}
