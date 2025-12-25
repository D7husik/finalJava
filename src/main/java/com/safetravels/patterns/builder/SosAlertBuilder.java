package com.safetravels.patterns.builder;

import com.safetravels.entity.Ride;
import com.safetravels.entity.SosEvent;
import com.safetravels.entity.User;
import com.safetravels.enums.SosStatus;

import java.time.LocalDateTime;

public class SosAlertBuilder {

    private Long id;
    private Ride ride;
    private User triggeredBy;
    private String message;
    private Double latitude;
    private Double longitude;
    private SosStatus status = SosStatus.SENT;
    private LocalDateTime createdAt = LocalDateTime.now();

    public SosAlertBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public SosAlertBuilder setRide(Ride ride) {
        this.ride = ride;
        return this;
    }

    public SosAlertBuilder setTriggeredBy(User triggeredBy) {
        this.triggeredBy = triggeredBy;
        return this;
    }

    public SosAlertBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public SosAlertBuilder setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public SosAlertBuilder setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public SosAlertBuilder setStatus(SosStatus status) {
        this.status = status;
        return this;
    }

    public SosEvent build() {
        SosEvent sosEvent = new SosEvent();
        sosEvent.setId(id);
        sosEvent.setRide(ride);
        sosEvent.setTriggeredBy(triggeredBy);
        sosEvent.setMessage(message);
        sosEvent.setLatitude(latitude);
        sosEvent.setLongitude(longitude);
        sosEvent.setStatus(status);
        sosEvent.setCreatedAt(createdAt);
        return sosEvent;
    }
}
