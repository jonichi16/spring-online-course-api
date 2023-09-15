package com.jonichi.soc.services;

import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private final InstructorRepository repository;

    public InstructorServiceImpl(InstructorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerInstructor(Instructor instructor) {
        repository.save(instructor);
    }
}
