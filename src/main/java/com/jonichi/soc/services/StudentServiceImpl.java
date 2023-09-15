package com.jonichi.soc.services;

import com.jonichi.soc.models.Student;
import com.jonichi.soc.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerStudent(Student student) {
        repository.save(student);
    }
}
