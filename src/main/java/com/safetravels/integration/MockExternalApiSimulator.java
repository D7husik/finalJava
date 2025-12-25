package com.safetravels.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockExternalApiSimulator {

    public boolean sendSms(String phoneNumber, String message) {
        log.info("📱 MOCK SMS API: Sending SMS to {}", phoneNumber);
        log.info("Message: {}", message);
        // Simulate API call delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    public boolean sendEmail(String email, String subject, String body) {
        log.info("📧 MOCK EMAIL API: Sending email to {}", email);
        log.info("Subject: {}", subject);
        log.info("Body: {}", body);
        return true;
    }

    public boolean sendPushNotification(String deviceToken, String message) {
        log.info("🔔 MOCK PUSH API: Sending push notification to device");
        log.info("Message: {}", message);
        return true;
    }
}
