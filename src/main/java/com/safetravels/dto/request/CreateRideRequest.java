package com.safetravels.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRideRequest {

    @NotBlank(message = "Start address is required")
    private String startAddress;

    @NotBlank(message = "Destination address is required")
    private String destinationAddress;

    private String driverName;

    private String driverPlate;
}
