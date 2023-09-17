package com.jonichi.soc.services;

import com.jonichi.soc.models.User;
import com.jonichi.soc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUsersDetailsService implements UserDetailsService {

    // Dependency Injection
    @Autowired
    private final UserRepository repository;

    public JwtUsersDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    // throws clause in a method signature can be helpful for developers because it provides explicit information
    // that the method may throw an Exception
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = repository.findByUsername(s).orElseThrow();

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }

        // This code returns a UserDetails object representing the user found in the database
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
