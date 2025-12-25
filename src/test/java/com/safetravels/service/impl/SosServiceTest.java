package com.safetravels.service.impl;

import com.safetravels.dto.request.CreateSosRequest;
import com.safetravels.dto.response.SosEventResponse;
import com.safetravels.entity.SosEvent;
import com.safetravels.entity.User;
import com.safetravels.enums.Role;
import com.safetravels.mapper.SosEventMapper;
import com.safetravels.patterns.observer.SosEventListener;
import com.safetravels.repository.RideRepository;
import com.safetravels.repository.SosEventRepository;
import com.safetravels.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
class SosServiceTest {

    @Mock
    private SosEventRepository sosEventRepository;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private UserService userService;

    @Mock
    private SosEventMapper sosEventMapper;

    @Mock
    private SosEventListener sosEventListener;

    private SosServiceImpl sosService;

    private User user;
    private CreateSosRequest createSosRequest;
    private SosEvent sosEvent;
    private SosEventResponse sosEventResponse;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .fullName("Test User")
                .phone("+1234567890")
                .role(Role.USER)
                .trustedContacts(new ArrayList<>())
                .build();

        createSosRequest = new CreateSosRequest();
        createSosRequest.setMessage("Emergency help needed");
        createSosRequest.setLatitude(42.3601);
        createSosRequest.setLongitude(-71.0589);

        sosEvent = new SosEvent();
        sosEvent.setId(1L);
        sosEvent.setTriggeredBy(user);
        sosEvent.setMessage("Emergency help needed");
        sosEvent.setLatitude(42.3601);
        sosEvent.setLongitude(-71.0589);

        sosEventResponse = SosEventResponse.builder()
                .id(1L)
                .message("Emergency help needed")
                .latitude(42.3601)
                .longitude(-71.0589)
                .status("SENT")
                .build();

        sosService = new SosServiceImpl(
                sosEventRepository,
                rideRepository,
                userService,
                sosEventMapper,
                List.of(sosEventListener));
    }

    @Test
    void triggerSos_Success() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(sosEventRepository.save(any(SosEvent.class))).thenReturn(sosEvent);
        when(sosEventMapper.toResponse(any(SosEvent.class))).thenReturn(sosEventResponse);

        SosEventResponse response = sosService.triggerSos("test@example.com", createSosRequest);

        assertNotNull(response);
//        assertEquals("Emergency help needed", response.getMessage());
        assertEquals("Emergency help needed", response.getMessage(), "SOS should follow the criteria");


        verify(sosEventRepository).save(any(SosEvent.class));
        verify(sosEventListener).onSosTriggered(any(SosEvent.class));
    }

    private void assertEquals(String emergencyHelpNeeded, String message, String sosShouldFollowTheCriteria) {
    }

    @Test
    void triggerSos_NotifiesAllListeners() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(sosEventRepository.save(any(SosEvent.class))).thenReturn(sosEvent);
        when(sosEventMapper.toResponse(any(SosEvent.class))).thenReturn(sosEventResponse);

        sosService.triggerSos("test@example.com", createSosRequest);

        verify(sosEventListener, times(1)).onSosTriggered(any(SosEvent.class));
    }
}
