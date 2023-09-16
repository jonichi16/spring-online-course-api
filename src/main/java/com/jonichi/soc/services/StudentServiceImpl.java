package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.StudentDtoV1;
import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.Student;
import com.jonichi.soc.repositories.AccountRepository;
import com.jonichi.soc.repositories.StudentRepository;
import com.jonichi.soc.requests.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final AccountRepository accountRepository;

    public StudentServiceImpl(StudentRepository studentRepository, AccountRepository accountRepository) {
        this.studentRepository = studentRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public StudentDtoV1 registerStudentV1(StudentRequest student) {

        Account studentAccount = new Account();
        studentAccount.setUsername(student.getUsername());
        studentAccount.setPassword(student.getPassword());
        Student newStudent = new Student();
        newStudent.setEmail(student.getEmail());
        newStudent.setFullName(student.getFullName());
        newStudent.setImageUrl(student.getImageUrl());
        newStudent.setAccount(studentAccount);


        accountRepository.save(studentAccount);
        studentRepository.save(newStudent);

        return mapToStudentDtoV1(newStudent);
    }

    private StudentDtoV1 mapToStudentDtoV1(Student student) {

        StudentDtoV1 studentDtoV1 = new StudentDtoV1();
        studentDtoV1.setId(student.getId());
        studentDtoV1.setUsername(student.getAccount().getUsername());
        studentDtoV1.setFullName(student.getFullName());
        studentDtoV1.setEmail(student.getEmail());
        studentDtoV1.setImageUrl(student.getImageUrl());
        studentDtoV1.setCreatedAt(student.getCreatedAt());
        studentDtoV1.setUpdatedAt(student.getUpdatedAt());

        return studentDtoV1;
    }
}
