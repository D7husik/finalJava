package com.safetravels.service.impl;

import com.safetravels.dto.request.CreateRideRequest;
import com.safetravels.dto.response.RideResponse;
import com.safetravels.entity.Ride;
import com.safetravels.entity.User;
import com.safetravels.enums.RideStatus;
import com.safetravels.enums.Role;
import com.safetravels.exception.ResourceNotFoundException;
import com.safetravels.exception.UnauthorizedException;
import com.safetravels.exception.ValidationException;
import com.safetravels.mapper.RideMapper;
import com.safetravels.repository.RideRepository;
import com.safetravels.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private UserService userService;

    @Mock
    private RideMapper rideMapper;

    @InjectMocks
    private RideServiceImpl rideService;

    private User user;
    private User otherUser;
    private Ride ride;
    private CreateRideRequest createRideRequest;
    private RideResponse rideResponse;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .fullName("Test User")
                .phone("+1234567890")
                .role(Role.USER)
                .build();

        otherUser = User.builder()
                .id(2L)
                .email("other@example.com")
                .password("password")
                .fullName("Other User")
                .phone("+0987654321")
                .role(Role.USER)
                .build();

        createRideRequest = new CreateRideRequest();
        createRideRequest.setStartAddress("Start Address");
        createRideRequest.setDestinationAddress("Destination Address");
        createRideRequest.setDriverName("Driver Name");
        createRideRequest.setDriverPlate("ABC123");

        ride = Ride.builder()
                .id(1L)
                .owner(user)
                .startAddress("Start Address")
                .destinationAddress("Destination Address")
                .status(RideStatus.CREATED)
                .build();

        rideResponse = RideResponse.builder()
                .id(1L)
                .startAddress("Start Address")
                .destinationAddress("Destination Address")
                .status("CREATED")
                .build();
    }

    @Test
    void createRide_Success() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(rideRepository.save(any(Ride.class))).thenReturn(ride);
        when(rideMapper.toResponse(any(Ride.class))).thenReturn(rideResponse);

        RideResponse response = rideService.createRide("test@example.com", createRideRequest);

        assertNotNull(response);
        assertEquals("Start Address", response.getStartAddress());
        verify(rideRepository).save(any(Ride.class));
    }

    @Test
    void startRide_Success() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));
        when(rideRepository.save(any(Ride.class))).thenReturn(ride);
        when(rideMapper.toResponse(any(Ride.class))).thenReturn(rideResponse);

        RideResponse response = rideService.startRide("test@example.com", 1L);

        assertNotNull(response);
        verify(rideRepository).save(any(Ride.class));
    }

    @Test
    void startRide_InvalidStatus_ThrowsValidationException() {
        ride.setStatus(RideStatus.ONGOING);
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));

        assertThrows(ValidationException.class,
                () -> rideService.startRide("test@example.com", 1L));
    }

    @Test
    void endRide_Success() {
        ride.setStatus(RideStatus.ONGOING);
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));
        when(rideRepository.save(any(Ride.class))).thenReturn(ride);
        when(rideMapper.toResponse(any(Ride.class))).thenReturn(rideResponse);

        RideResponse response = rideService.endRide("test@example.com", 1L);

        assertNotNull(response);
        verify(rideRepository).save(any(Ride.class));
    }

    @Test
    void endRide_InvalidStatus_ThrowsValidationException() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));

        assertThrows(ValidationException.class,
                () -> rideService.endRide("test@example.com", 1L));
    }

    @Test
    void getRide_NotFound_ThrowsResourceNotFoundException() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(rideRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> rideService.getRide("test@example.com", 1L));
    }

    @Test
    void getRide_NotOwner_ThrowsUnauthorizedException() {
        when(userService.findUserByEmail(anyString())).thenReturn(otherUser);
        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));

        assertThrows(UnauthorizedException.class,
                () -> rideService.getRide("other@example.com", 1L));
    }
}
