package com.safetravels.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SosEventResponse {
    private Long id;
    private Long rideId;
    private String message;
    private Double latitude;
    private Double longitude;
    private String status;
    private LocalDateTime createdAt;
}
