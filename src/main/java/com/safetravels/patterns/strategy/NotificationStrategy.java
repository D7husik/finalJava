package com.safetravels.patterns.strategy;

public interface NotificationStrategy {
    boolean send(String recipient, String message);

    String getChannelType();
}
