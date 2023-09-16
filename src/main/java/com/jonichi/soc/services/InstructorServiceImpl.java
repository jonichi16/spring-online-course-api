package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.InstructorDtoV1;
import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.models.Student;
import com.jonichi.soc.repositories.AccountRepository;
import com.jonichi.soc.repositories.InstructorRepository;
import com.jonichi.soc.requests.InstructorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private final InstructorRepository instructorRepository;

    @Autowired
    private final AccountRepository accountRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, AccountRepository accountRepository) {
        this.instructorRepository = instructorRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public InstructorDtoV1 registerInstructorV1(InstructorRequest instructor) {

        Account instructorAccount = new Account();
        instructorAccount.setUsername(instructor.getUsername());
        instructorAccount.setPassword(instructor.getPassword());
        Instructor newInstructor = new Instructor();
        newInstructor.setEmail(instructor.getEmail());
        newInstructor.setFullName(instructor.getFullName());
        newInstructor.setImageUrl(instructor.getImageUrl());
        newInstructor.setAccount(instructorAccount);

        accountRepository.save(instructorAccount);
        instructorRepository.save(newInstructor);

        return mapToInstructorDtoV1(newInstructor);
    }

    private InstructorDtoV1 mapToInstructorDtoV1(Instructor instructor) {
        InstructorDtoV1 instructorDtoV1 = new InstructorDtoV1();
        instructorDtoV1.setId(instructor.getId());
        instructorDtoV1.setUsername(instructor.getAccount().getUsername());
        instructorDtoV1.setEmail(instructor.getEmail());
        instructorDtoV1.setFullName(instructor.getFullName());
        instructorDtoV1.setImageUrl(instructor.getImageUrl());
        instructorDtoV1.setCreatedAt(instructor.getCreatedAt());
        instructorDtoV1.setUpdatedAt(instructor.getUpdatedAt());

        return instructorDtoV1;
    }
}
