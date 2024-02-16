package development.apodbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import development.apodbackend.models.SentEmailModel;

public interface SentEmailRepository extends JpaRepository <SentEmailModel, Integer> {
    List<SentEmailModel> findBySubscriptionIdOrderByCreatedAtDesc(@Param("subscriptionId") int subscriptionId);}
