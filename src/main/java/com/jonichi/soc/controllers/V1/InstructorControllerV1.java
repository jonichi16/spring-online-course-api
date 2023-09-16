package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.requests.InstructorRequest;
import com.jonichi.soc.responses.V1.ApiResponseV1;
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

        HttpStatus status = HttpStatus.CREATED;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        instructorService.registerInstructorV1(instructor),
                        "Instructor registered successfully!"
                ),
                HttpStatus.CREATED
        );
    }

}
