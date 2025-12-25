package com.safetravels.service.impl;

import com.safetravels.dto.request.CreateTrustedContactRequest;
import com.safetravels.dto.response.TrustedContactResponse;
import com.safetravels.entity.TrustedContact;
import com.safetravels.entity.User;
import com.safetravels.exception.ResourceNotFoundException;
import com.safetravels.exception.UnauthorizedException;
import com.safetravels.mapper.TrustedContactMapper;
import com.safetravels.repository.TrustedContactRepository;
import com.safetravels.service.TrustedContactService;
import com.safetravels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrustedContactServiceImpl implements TrustedContactService {

    private final TrustedContactRepository contactRepository;
    private final UserService userService;
    private final TrustedContactMapper contactMapper;

    @Override
    @Transactional
    public TrustedContactResponse createTrustedContact(String ownerEmail, CreateTrustedContactRequest request) {
        User owner = userService.findUserByEmail(ownerEmail);

        TrustedContact contact = TrustedContact.builder()
                .owner(owner)
                .contactName(request.getContactName())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .relation(request.getRelation())
                .accepted(false)
                .build();

        contact = contactRepository.save(contact);
        return contactMapper.toResponse(contact);
    }

    @Override
    public List<TrustedContactResponse> getTrustedContacts(String ownerEmail) {
        User owner = userService.findUserByEmail(ownerEmail);
        return contactRepository.findByOwner(owner).stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTrustedContact(String ownerEmail, Long contactId) {
        User owner = userService.findUserByEmail(ownerEmail);
        TrustedContact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Trusted contact not found"));

        if (!contact.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedException("You can only delete your own contacts");
        }

        contactRepository.delete(contact);
    }
}
