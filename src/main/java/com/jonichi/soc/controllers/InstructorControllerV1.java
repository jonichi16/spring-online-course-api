package com.jonichi.soc.controllers;

import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.requests.InstructorRequest;
import com.jonichi.soc.services.AccountService;
import com.jonichi.soc.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/instructors")
public class InstructorControllerV1 {

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final InstructorService instructorService;

    public InstructorControllerV1(AccountService accountService, InstructorService instructorService) {
        this.accountService = accountService;
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<Object> registerInstructor(@RequestBody InstructorRequest instructor) {
        Account instructorAccount = new Account();
        instructorAccount.setUsername(instructor.getUsername());
        instructorAccount.setPassword(instructor.getPassword());
        Instructor newInstructor = new Instructor();
        newInstructor.setEmail(instructor.getEmail());
        newInstructor.setAccount(instructorAccount);

        accountService.registerAccount(instructorAccount);
        instructorService.registerInstructor(newInstructor);

        return new ResponseEntity<>(
                newInstructor,
                HttpStatus.CREATED
        );
    }

}
