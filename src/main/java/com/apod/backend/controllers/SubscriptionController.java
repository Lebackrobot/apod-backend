package com.apod.backend.controllers;

import com.apod.backend.Dtos.SubscriptionDto;
import com.apod.backend.entities.Subscription;
import com.apod.backend.services.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody @Valid SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionService.getByEmail(subscriptionDto.email());

        if (subscription != null) {
            return ResponseEntity.status(409).build();
        }

        Subscription newSubscription = subscriptionService.create(
            new Subscription(subscriptionDto.email(), subscriptionDto.name())
        );

        return ResponseEntity.status(201).body(newSubscription);
    }
}