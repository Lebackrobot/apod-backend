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

import development.apodbackend.models.UserModel;
import development.apodbackend.schemas.UserSchema;
import development.apodbackend.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/noauth/users")
public class UserController {

    @Autowired 
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserModel>> get() {
        return ResponseEntity.ok(userService.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserModel> getById(@PathVariable int id) {
        var user = userService.getById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserSchema payload) {
        
        var user = userService.getByEmail(payload.email());

        if (user != null) {
            return ResponseEntity.status(409).build();
        }
        
        return ResponseEntity.status(201).body(userService.createUser(new UserModel(payload)));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<UserModel> deleteById(@PathVariable int id) {
        var user = userService.getById(id); 

        if (user.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        userService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
