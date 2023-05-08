package com.team4822.studyup.controllers;

import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    @Inject
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("Пользователь успешно зарегистрирован");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            return ResponseEntity.status(HttpStatus.OK).body(userDetails.getPassword().equals(user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

    @GetMapping("/confirm-email/{token}")
    public ResponseEntity<?> confirmRegistration(@PathVariable String token) {
        try {
            userService.confirmRegistration(token);
            return ResponseEntity.status(HttpStatus.OK).body("Адрес электронной почты подтвержден");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/email")
    public ResponseEntity<?> changeEmail(String newEmail){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.changeEmail(authentication.getName(), newEmail);
            return ResponseEntity.status(HttpStatus.OK).body("Адрес электронной почты успешно изменен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
