package com.team4822.studyup.services;

import com.team4822.studyup.models.authentication.ConfirmationToken;
import com.team4822.studyup.models.authentication.Role;
import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Inject
    public UserService(UserRepository userRepository, ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepository.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(),
                mapRolesToAuthorities(myUser.getRoles()));
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    public void addUser(User user) throws Exception{
        User userFormDb = userRepository.findByEmail(user.getEmail());
        if (userFormDb != null){
            throw new Exception("user exist");
        }

        ConfirmationToken token = new ConfirmationToken(user);
        emailService.sendMail(user.getEmail(), "Токен для подтверждения почты", token.getConfirmationToken());
        user.setConfirmed(false);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        confirmationTokenService.saveConfirmationToken(token);
    }

    public void changeEmail(String email, String newEmail){
        User user = userRepository.findByEmail(email);
        ConfirmationToken token = new ConfirmationToken(user);
        user.setEmail(newEmail);
        emailService.sendMail(user.getEmail(), "Токен для подтверждения почты", token.getConfirmationToken());
        user.setConfirmed(false);
        userRepository.save(user);
        confirmationTokenService.saveConfirmationToken(token);
    }

    public void confirmRegistration(String token) throws Exception {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);
        if (confirmationToken == null) {
            throw new Exception("Invalid token!");
        }
        User user = confirmationToken.getUser();
        user.setConfirmed(true);
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getTokenId());
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(long id){
        return userRepository.findById(id).orElseThrow();
    }
}
