package com.jonichi.soc.services;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addCourse(Long id, Course course) {

        Instructor instructor = repository.getInstructorByAccount(id).orElseThrow();
        course.setInstructor(instructor);

        repository.save(course);
    }

    @Override
    public Iterable<Course> getAllCourses() {
        return repository.findAll();
    }

    @Override
    public Course getCourse(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
