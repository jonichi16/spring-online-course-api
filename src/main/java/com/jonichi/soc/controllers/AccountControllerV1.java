package com.jonichi.soc.controllers;

import com.jonichi.soc.models.Account;
import com.jonichi.soc.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/accounts")
public class AccountControllerV1 {

    @Autowired
    private final AccountService service;

    public AccountControllerV1(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> registerAccount(@RequestBody Account account) {
        service.registerAccount(account);

        return new ResponseEntity<>(
                account,
                HttpStatus.CREATED
        );
    }

}
