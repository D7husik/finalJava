package com.safetravels.patterns.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InAppNotificationStrategy implements NotificationStrategy {

    @Override
    public boolean send(String recipient, String message) {
        log.info("📱 IN-APP Notification sent to {}: {}", recipient, message);
        // Simulate in-app notification
        return true;
    }

    @Override
    public String getChannelType() {
        return "IN_APP";
    }
}
