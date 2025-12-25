package com.safetravels.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrustedContactResponse {
    private Long id;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String relation;
    private Boolean accepted;
}
