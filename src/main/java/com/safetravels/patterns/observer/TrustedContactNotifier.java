package com.safetravels.patterns.observer;

import com.safetravels.entity.SosEvent;
import com.safetravels.entity.TrustedContact;
import com.safetravels.enums.NotificationChannel;
import com.safetravels.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrustedContactNotifier implements SosEventListener {

    private final NotificationService notificationService;

    @Override
    public void onSosTriggered(SosEvent sosEvent) {
        log.info("Observer triggered: Notifying trusted contacts for SOS Event ID: {}", sosEvent.getId());
        List<TrustedContact> contacts = sosEvent.getTriggeredBy().getTrustedContacts();

        String message = String.format(
                "🚨 EMERGENCY ALERT from %s: %s. Location: (%.6f, %.6f)",
                sosEvent.getTriggeredBy().getFullName(),
                sosEvent.getMessage() != null ? sosEvent.getMessage() : "No message provided",
                sosEvent.getLatitude() != null ? sosEvent.getLatitude() : 0.0,
                sosEvent.getLongitude() != null ? sosEvent.getLongitude() : 0.0);

        for (TrustedContact contact : contacts) {
            notificationService.sendNotification(
                    sosEvent.getId(),
                    contact.getContactEmail(),
                    message,
                    NotificationChannel.CONSOLE);
        }
    }
}
