package com.safetravels.repository;

import com.safetravels.entity.Ride;
import com.safetravels.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByOwnerOrderByCreatedAtDesc(User owner);
}
