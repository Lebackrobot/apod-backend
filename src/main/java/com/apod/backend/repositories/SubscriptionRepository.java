package com.apod.backend.repositories;

import com.apod.backend.entities.Subscription;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
