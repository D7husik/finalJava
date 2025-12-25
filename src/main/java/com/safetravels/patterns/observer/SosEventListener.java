package com.safetravels.patterns.observer;

import com.safetravels.entity.SosEvent;

public interface SosEventListener {
    void onSosTriggered(SosEvent sosEvent);
}
