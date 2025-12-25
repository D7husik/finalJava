package com.safetravels.service;

import com.safetravels.dto.request.CreateSosRequest;
import com.safetravels.dto.response.SosEventResponse;

import java.util.List;

public interface SosService {
    SosEventResponse triggerSos(String userEmail, CreateSosRequest request);

    List<SosEventResponse> getUserSosEvents(String userEmail);

    SosEventResponse getSosEvent(String userEmail, Long sosId);
}
