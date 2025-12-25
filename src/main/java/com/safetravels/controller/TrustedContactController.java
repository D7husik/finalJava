package com.safetravels.controller;

import com.safetravels.dto.request.CreateTrustedContactRequest;
import com.safetravels.dto.response.TrustedContactResponse;
import com.safetravels.service.TrustedContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/me/trusted-contacts")
@RequiredArgsConstructor
public class TrustedContactController {

    private final TrustedContactService trustedContactService;

    @PostMapping
    public ResponseEntity<TrustedContactResponse> createTrustedContact(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateTrustedContactRequest request) {
        TrustedContactResponse response = trustedContactService.createTrustedContact(
                userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TrustedContactResponse>> getTrustedContacts(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<TrustedContactResponse> contacts = trustedContactService.getTrustedContacts(
                userDetails.getUsername());
        return ResponseEntity.ok(contacts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrustedContact(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        trustedContactService.deleteTrustedContact(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}
