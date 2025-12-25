package com.safetravels.mapper;

import com.safetravels.dto.response.TrustedContactResponse;
import com.safetravels.entity.TrustedContact;
import org.springframework.stereotype.Component;

@Component
public class TrustedContactMapper {

    public TrustedContactResponse toResponse(TrustedContact contact) {
        return TrustedContactResponse.builder()
                .id(contact.getId())
                .contactName(contact.getContactName())
                .contactEmail(contact.getContactEmail())
                .contactPhone(contact.getContactPhone())
                .relation(contact.getRelation())
                .accepted(contact.getAccepted())
                .build();
    }
}
