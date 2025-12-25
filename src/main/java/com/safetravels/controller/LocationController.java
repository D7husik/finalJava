package com.safetravels.controller;

import com.safetravels.dto.request.UpdateLocationRequest;
import com.safetravels.dto.response.LocationResponse;
import com.safetravels.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/{id}/location")
    public ResponseEntity<LocationResponse> updateLocation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody UpdateLocationRequest request) {
        LocationResponse response = locationService.updateLocation(
                userDetails.getUsername(), id, request);
        return ResponseEntity.ok(response);
    }
}
