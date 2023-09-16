package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.config.JwtToken;
import com.jonichi.soc.models.Account;
import com.jonichi.soc.repositories.AccountRepository;
import com.jonichi.soc.requests.JwtRequest;
import com.jonichi.soc.responses.V1.JwtResponseV1;
import com.jonichi.soc.services.JwtUsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/auth")
public class AuthControllerV1 {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUsersDetailsService jwtUserDetailsService;

    @Autowired
    private AccountRepository repository;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest jwtRequest
    ) throws Exception {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final Account account = repository.findByUsername(jwtRequest.getUsername());
        final HttpStatus status = HttpStatus.OK;
        final String token = jwtToken.generateToken(userDetails);
        final String message = "Login Successful";

        return new ResponseEntity<>(
                new JwtResponseV1(
                        token,
                        account,
                        status.value(),
                        message
                ),
                status
        );
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            // The purpose of catching the DisabledException is to provide error handling for disabled
            // user accounts.
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            // The purpose of catching the BadCredentialsException it to provide error handling invalid
            // user credentials
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
