package com.apod.backend.controllers;

import com.apod.backend.dtos.payloads.SubscriptionConfirmationPayloadDto;
import com.apod.backend.dtos.responses.ResponseDto;
import com.apod.backend.entities.Subscription;
import com.apod.backend.services.RedisService;
import com.apod.backend.services.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription-confirmations")
public class SubscriptionConfirmationController {
    @Autowired
    RedisService redisService;

    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<ResponseDto<Subscription>> confirmation(@RequestBody @Valid SubscriptionConfirmationPayloadDto subscriptionValidationPayload) {
        String token = redisService.get(subscriptionValidationPayload.email());

        if (!subscriptionValidationPayload.token().equals(token)) {
            return ResponseEntity.status(401).body(
                    new ResponseDto<>(false, "Token inválido.", null)
            );
        }

        Subscription newSubscription = subscriptionService.create(
                new Subscription(
                        subscriptionValidationPayload.email(),
                        subscriptionValidationPayload.name()
                )
        );

        redisService.delete(subscriptionValidationPayload.email());
        return ResponseEntity.status(201).body(new ResponseDto<Subscription>(true, "Subscription criada.", newSubscription));
    }
}
