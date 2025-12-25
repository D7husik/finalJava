package com.safetravels.controller;

import com.safetravels.dto.request.CreateSosRequest;
import com.safetravels.dto.response.SosEventResponse;
import com.safetravels.service.SosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
@RequiredArgsConstructor
public class SosController {

    private final SosService sosService;

    @PostMapping
    public ResponseEntity<SosEventResponse> triggerSos(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateSosRequest request) {
        SosEventResponse response = sosService.triggerSos(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SosEventResponse>> getUserSosEvents(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<SosEventResponse> events = sosService.getUserSosEvents(userDetails.getUsername());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SosEventResponse> getSosEvent(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        SosEventResponse response = sosService.getSosEvent(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }
}
