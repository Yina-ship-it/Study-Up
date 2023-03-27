package com.team4822.studyup.controllers;

import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.addUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/confirm-account/{token}")
    public String confirmRegistration(@PathVariable String token) {
        try {
            userService.confirmRegistration(token);
            return "всё тру";
        } catch (Exception e) {
            return "всё фолз";
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.findAll();
    }
}
