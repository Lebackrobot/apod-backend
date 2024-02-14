package development.apodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import development.apodbackend.models.SentEmailModel;

public interface SentEmailRepository extends JpaRepository <SentEmailModel, Integer> {}
