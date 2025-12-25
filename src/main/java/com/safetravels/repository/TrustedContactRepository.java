package com.safetravels.repository;

import com.safetravels.entity.TrustedContact;
import com.safetravels.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrustedContactRepository extends JpaRepository<TrustedContact, Long> {
    List<TrustedContact> findByOwner(User owner);
}
