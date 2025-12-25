package com.safetravels.controller;

import com.safetravels.enums.NotificationChannel;
import com.safetravels.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/simulate")
    public ResponseEntity<Map<String, String>> simulateNotification(
            @RequestBody Map<String, String> request) {
        String recipient = request.get("recipient");
        String message = request.get("message");
        String channel = request.getOrDefault("channel", "CONSOLE");

        notificationService.sendNotification(
                0L,
                recipient,
                message,
                NotificationChannel.valueOf(channel));

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Notification simulated successfully"));
    }
}
