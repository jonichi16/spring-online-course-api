package com.jonichi.soc.services;

import com.jonichi.soc.models.Account;
import com.jonichi.soc.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUsersDetailsService implements UserDetailsService {

    // Dependency Injection
    @Autowired
    private final AccountRepository repository;

    public JwtUsersDetailsService(AccountRepository repository) {
        this.repository = repository;
    }

    // throws clause in a method signature can be helpful for developers because it provides explicit information
    // that the method may throw an Exception
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Account account = repository.findByUsername(s);

        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }

        // This code returns a UserDetails object representing the user found in the database
        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                new ArrayList<>()
        );
    }
}
