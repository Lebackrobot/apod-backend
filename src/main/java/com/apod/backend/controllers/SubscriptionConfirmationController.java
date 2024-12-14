package com.apod.backend.controllers;

import com.apod.backend.dtos.payloads.SubscriptionConfirmationPayloadDto;
import com.apod.backend.dtos.payloads.SubscriptionPayloadDto;
import com.apod.backend.dtos.rabbitMessages.RabbitMessageDto;
import com.apod.backend.dtos.responses.ResponseDto;
import com.apod.backend.entities.Subscription;
import com.apod.backend.services.RabbitService;
import com.apod.backend.services.RedisService;
import com.apod.backend.services.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RabbitService rabbitService;

    @PostMapping
    public ResponseEntity<ResponseDto<Subscription>> confirmation(@RequestBody @Valid SubscriptionConfirmationPayloadDto subscriptionValidationPayload) {
        try {
            String subscriptionJson = redisService.get(subscriptionValidationPayload.token());

            if (subscriptionJson == null) {
                return ResponseEntity.status(401).body(
                        new ResponseDto<>(false, "Token inválido.", null)
                );
            }

            SubscriptionPayloadDto subscriptionDto = objectMapper.readValue(subscriptionJson, SubscriptionPayloadDto.class);

            Subscription newSubscription = subscriptionService.create(
                    new Subscription(subscriptionDto.email(), subscriptionDto.name())
            );

            redisService.delete(subscriptionValidationPayload.token());

            var rabbitMessage = new RabbitMessageDto(
                    newSubscription.getEmail(),
                    newSubscription.getName(),
                    null,
                    "subscription"
            );

            var rabbitMessagedDtoJson = objectMapper.writeValueAsString(rabbitMessage);
            rabbitService.send(rabbitMessagedDtoJson);

            return ResponseEntity.status(201).body(new ResponseDto<Subscription>(true, "Subscription criada.", newSubscription));
        }

        catch (Exception error) {
            return ResponseEntity.status(401).body(
                    new ResponseDto<>(false, error.getMessage(), null)
            );
        }
    }
}
