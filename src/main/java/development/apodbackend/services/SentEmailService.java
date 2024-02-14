package development.apodbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import development.apodbackend.models.SentEmailModel;
import development.apodbackend.repositories.SentEmailRepository;

@Service
public class SentEmailService {
    @Autowired
    private SentEmailRepository repository;

    public SentEmailModel create(SentEmailModel sentEmail) {
        if (sentEmail == null) {
            return null;
        }

        repository.save(sentEmail);
        return sentEmail;
    }
}
