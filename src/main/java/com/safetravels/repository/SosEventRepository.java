package com.safetravels.repository;

import com.safetravels.entity.SosEvent;
import com.safetravels.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SosEventRepository extends JpaRepository<SosEvent, Long> {
    List<SosEvent> findByTriggeredByOrderByCreatedAtDesc(User triggeredBy);
}
