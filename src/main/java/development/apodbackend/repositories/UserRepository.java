package development.apodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import development.apodbackend.models.UserModel;

public interface UserRepository extends JpaRepository <UserModel, Integer>{
    UserModel findOneByEmail(String email);
}
