package com.jonichi.soc.controllers;

import com.jonichi.soc.dto.StudentDto;
import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.Student;
import com.jonichi.soc.models.User;
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
    public ResponseEntity<Object> regiterStudent(@RequestBody StudentDto student) {
        Student newStudent = new Student();
        newStudent.setEmail(student.getEmail());
        Account studentAccount = new Account();
        studentAccount.setUsername(student.getUsername());
        studentAccount.setPassword(student.getPassword());
        studentAccount.setStudent(newStudent);

        accountService.registerAccount(studentAccount);
        studentService.registerStudent(newStudent);

        return new ResponseEntity<>(
                newStudent,
                HttpStatus.CREATED
        );
    }
}