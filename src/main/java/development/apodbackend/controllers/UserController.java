package development.apodbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import development.apodbackend.models.UserModel;
import development.apodbackend.services.UserService;

@RestController
@RequestMapping("/noauth/users")
public class UserController {

    @Autowired 
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
