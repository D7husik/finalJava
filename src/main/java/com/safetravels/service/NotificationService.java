package com.safetravels.service;

import com.safetravels.enums.NotificationChannel;

public interface NotificationService {
    void sendNotification(Long sosEventId, String recipient, String message, NotificationChannel channel);
}
