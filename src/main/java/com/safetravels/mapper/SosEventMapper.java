package com.safetravels.mapper;

import com.safetravels.dto.response.SosEventResponse;
import com.safetravels.entity.SosEvent;
import org.springframework.stereotype.Component;

@Component
public class SosEventMapper {

    public SosEventResponse toResponse(SosEvent sosEvent) {
        return SosEventResponse.builder()
                .id(sosEvent.getId())
                .rideId(sosEvent.getRide() != null ? sosEvent.getRide().getId() : null)
                .message(sosEvent.getMessage())
                .latitude(sosEvent.getLatitude())
                .longitude(sosEvent.getLongitude())
                .status(sosEvent.getStatus().name())
                .createdAt(sosEvent.getCreatedAt())
                .build();
    }
}
