package com.apod.backend.repositories;

import com.apod.backend.entities.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    public Optional<Subscription> findByEmail(String email);
}
