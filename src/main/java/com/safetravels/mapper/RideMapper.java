package com.safetravels.mapper;

import com.safetravels.dto.response.RideResponse;
import com.safetravels.entity.Ride;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    public RideResponse toResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .startAddress(ride.getStartAddress())
                .destinationAddress(ride.getDestinationAddress())
                .driverName(ride.getDriverName())
                .driverPlate(ride.getDriverPlate())
                .status(ride.getStatus().name())
                .startTime(ride.getStartTime())
                .endTime(ride.getEndTime())
                .createdAt(ride.getCreatedAt())
                .build();
    }
}
