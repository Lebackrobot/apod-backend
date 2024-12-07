package com.apod.backend.services;

import com.apod.backend.entities.Subscription;
import com.apod.backend.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription create(Subscription subscription) {
        System.out.println(subscription);
        return subscriptionRepository.save(subscription);
    }

    public void update(Subscription subscription) { subscriptionRepository.save(subscription);}

    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription getByEmail(String email) {
        return subscriptionRepository.findByEmail(email).orElse(null);
    }

    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
