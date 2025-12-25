package com.safetravels.patterns.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleNotificationStrategy implements NotificationStrategy {

    @Override
    public boolean send(String recipient, String message) {
        log.info("🖥️ CONSOLE Notification sent to {}: {}", recipient, message);
        System.out.println("=== CONSOLE NOTIFICATION ===");
        System.out.println("To: " + recipient);
        System.out.println("Message: " + message);
        System.out.println("===========================");
        return true;
    }

    @Override
    public String getChannelType() {
        return "CONSOLE";
    }
}
