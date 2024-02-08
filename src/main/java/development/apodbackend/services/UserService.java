package development.apodbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import development.apodbackend.models.UserModel;
import development.apodbackend.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserModel> get() {
        return repository.findAll();
    }

    public Optional<UserModel> getById(int id) {
        return repository.findById(id);
    }

    public UserModel createUser(UserModel user) {
        return repository.save(user);
    }

    public UserModel getByEmail(String email) {
        return repository.findOneByEmail(email);
    }
    
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
