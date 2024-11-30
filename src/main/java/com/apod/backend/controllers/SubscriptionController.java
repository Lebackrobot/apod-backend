package com.apod.backend.controllers;

import com.apod.backend.dtos.payloads.SubscriptionValidationPayloadDto;
import com.apod.backend.dtos.responses.ResponseDto;
import com.apod.backend.dtos.payloads.SubscriptionPayloadDto;
import com.apod.backend.entities.Subscription;
import com.apod.backend.services.RedisService;
import com.apod.backend.services.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private RedisService redisService;

    @PostMapping
    public ResponseEntity<ResponseDto<Null>> create(@RequestBody @Valid SubscriptionPayloadDto subscriptionPayload) {
        Subscription subscription = subscriptionService.getByEmail(subscriptionPayload.email());

        if (subscription != null) {
            return ResponseEntity.status(409).build();
        }

        redisService.setTokenByEmail(subscriptionPayload.email());

        // 📨 Send token to your email

        return ResponseEntity.status(202).body(
                new ResponseDto<Null>(true, "Token de confirmação enviado para o email.", null)
        );
    }

    @PostMapping("confirmations")
    public ResponseEntity<ResponseDto<Subscription>> confirmation(@RequestBody @Valid SubscriptionValidationPayloadDto subscriptionValidationPayload) {
        String token = redisService.get(subscriptionValidationPayload.email());

        if (token == null) {
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
        return ResponseEntity.status(201).body(new ResponseDto<Subscription>(true, "Subscription criada.", newSubscription));
    }
}