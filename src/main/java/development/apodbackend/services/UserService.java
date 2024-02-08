package development.apodbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import development.apodbackend.models.UserModel;
import development.apodbackend.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserModel> getAll() {
        return repository.findAll();
    }
}
