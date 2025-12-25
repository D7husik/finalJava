package com.safetravels.service.impl;

import com.safetravels.entity.NotificationLog;
import com.safetravels.enums.NotificationChannel;
import com.safetravels.patterns.factory.NotificationFactory;
import com.safetravels.patterns.strategy.NotificationStrategy;
import com.safetravels.repository.NotificationLogRepository;
import com.safetravels.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationFactory notificationFactory;
    private final NotificationLogRepository notificationLogRepository;

    @Override
    @Transactional
    public void sendNotification(Long sosEventId, String recipient, String message, NotificationChannel channel) {
        // Strategy Pattern: Get appropriate notification strategy
        NotificationStrategy strategy = notificationFactory.getStrategy(channel);
        boolean success = strategy.send(recipient, message);

        // Log the notification
        NotificationLog notificationLog = NotificationLog.builder()
                .sosEventId(sosEventId)
                .targetContact(recipient)
                .channel(channel)
                .message(message)
                .success(success)
                .build();

        notificationLogRepository.save(notificationLog);
    }
}
