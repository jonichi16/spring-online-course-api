package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.dto.V1.StudentDtoV1;
import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.Student;
import com.jonichi.soc.requests.StudentRequest;
import com.jonichi.soc.responses.V1.ApiResponseV1;
import com.jonichi.soc.services.AccountService;
import com.jonichi.soc.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/students")
public class StudentControllerV1 {

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final StudentService studentService;

    public StudentControllerV1(AccountService accountService, StudentService studentService) {
        this.accountService = accountService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseV1> registerStudent(@RequestBody StudentRequest student) {

        HttpStatus status = HttpStatus.CREATED;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        studentService.registerStudentV1(student),
                        "Student registered successfully"
                ),
                status
        );
    }
}
