package com.team4822.studyup.services;

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(),
                mapRolesToAuthorities(myUser.getRoles()));
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    public User addUser(User user) throws Exception{
        User userFormDb = userRepository.findByUsername(user.getUsername());
        if (userFormDb != null){
            throw new Exception("user exist");
        }
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
