package com.safetravels.mapper;

import com.safetravels.dto.response.LocationResponse;
import com.safetravels.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationResponse toResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .rideId(location.getRide().getId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .timestamp(location.getTimestamp())
                .build();
    }
}
