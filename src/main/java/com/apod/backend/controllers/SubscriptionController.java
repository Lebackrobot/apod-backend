package com.apod.backend.controllers;

import com.apod.backend.dtos.responses.ResponseDto;
import com.apod.backend.dtos.payloads.SubscriptionPayloadDto;
import com.apod.backend.entities.Subscription;
import com.apod.backend.entities.TokenService;
import com.apod.backend.services.RedisService;
import com.apod.backend.services.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<ResponseDto<Null>> create(@RequestBody @Valid SubscriptionPayloadDto subscriptionPayload) {
        try {
            Subscription subscription = subscriptionService.getByEmail(subscriptionPayload.email());

            if (subscription != null) {
                return ResponseEntity.status(409).body(new ResponseDto<>(false, "Email já cadastrado", null));
            }

            var redisKey = tokenService.generateToken();
            var redisValue = objectMapper.writeValueAsString(subscriptionPayload);

            redisService.set(redisKey, redisValue);

            // 📨 Send token to your email

            return ResponseEntity.status(202).body(
                    new ResponseDto<Null>(true, "Token de confirmação enviado para o email.", null)
            );
        }

        catch (Exception error) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>(false, error.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<ResponseDto<Subscription>> deleteById(@PathVariable Long subscriptionId) {
        Subscription subscription = subscriptionService.getById(subscriptionId);

        if (subscription == null) {
            return ResponseEntity.status(404).body(new ResponseDto<>(false, "Subscription não encontrada.", null));
        }

        subscriptionService.deleteById(subscriptionId);
        return ResponseEntity.status(200).body(new ResponseDto<>(true, "Subscription deletada.", subscription));
    }
}