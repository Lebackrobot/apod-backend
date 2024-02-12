package development.apodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import development.apodbackend.models.SubscriptionModel;

public interface SubscriptionRepository extends JpaRepository <SubscriptionModel, Integer>{
    SubscriptionModel findOneByEmail(String email);
}
