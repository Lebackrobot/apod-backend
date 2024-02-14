package development.apodbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import development.apodbackend.models.SubscriptionModel;
import development.apodbackend.schemas.SubscriptionSchema;
import development.apodbackend.services.SchedulerEmailService;
import development.apodbackend.services.SubscriptionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/noauth/subscriptions")
public class SubscriptionController {

    @Autowired 
    private SubscriptionService subscriptionService;

    @Autowired
    private SchedulerEmailService mailSenderService;
    
    @GetMapping
    public ResponseEntity<List<SubscriptionModel>> get() {
        return ResponseEntity.ok(subscriptionService.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<SubscriptionModel> getById(@PathVariable int id) {
        var subscription = subscriptionService.getById(id);

        if (subscription.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(subscription.get());
    }

    @PostMapping
    public ResponseEntity<SubscriptionModel> create(@RequestBody @Valid SubscriptionSchema payload) {
        
        var subscription = subscriptionService.getByEmail(payload.email());

        if (subscription != null) {
            return ResponseEntity.status(409).build();
        }

        if (payload.email()  == null || payload.username() == null) {
            return ResponseEntity.status(400).build();
        }

        
        // Send email to new subscription
        final SubscriptionModel newSubscription = subscriptionService.create(new SubscriptionModel(payload));
        
        new Thread(() -> {
            mailSenderService.sendEmail(newSubscription);
        }).start();
        
        return ResponseEntity.status(201).body(newSubscription);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<SubscriptionModel> deleteById(@PathVariable int id) {
        var subscription = subscriptionService.getById(id); 

        if (subscription.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        subscriptionService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
