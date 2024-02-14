package development.apodbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import development.apodbackend.models.SubscriptionModel;
import development.apodbackend.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository repository;

    public List<SubscriptionModel> get() {
        return repository.findAll();
    }

    public Optional<SubscriptionModel> getById(int id) {
        return repository.findById(id);
    }

    public SubscriptionModel create(SubscriptionModel subscription) {
        if (subscription == null) {
            return null;
        }
        return repository.save(subscription);
    }

    public SubscriptionModel getByEmail(String email) {
        return repository.findOneByEmail(email);
    }
    
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
