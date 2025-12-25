package com.safetravels.patterns.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockSmsNotificationStrategy implements NotificationStrategy {

    @Override
    public boolean send(String recipient, String message) {
        log.info("📧 SMS Notification (MOCK) sent to {}: {}", recipient, message);
        // Simulate SMS sending
        return true;
    }

    @Override
    public String getChannelType() {
        return "SMS";
    }
}
