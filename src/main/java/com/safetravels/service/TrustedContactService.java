package com.safetravels.service;

import com.safetravels.dto.request.CreateTrustedContactRequest;
import com.safetravels.dto.response.TrustedContactResponse;

import java.util.List;

public interface TrustedContactService {
    TrustedContactResponse createTrustedContact(String ownerEmail, CreateTrustedContactRequest request);

    List<TrustedContactResponse> getTrustedContacts(String ownerEmail);

    void deleteTrustedContact(String ownerEmail, Long contactId);
}
