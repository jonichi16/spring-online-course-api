package com.jonichi.soc.services;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return repository.getAvailableCourses();
    }

    @Override
    public Course getCourse(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Course updateCourse(Long accountId, Long courseId, Course course) throws Exception {

        Instructor instructor = repository.getInstructorByAccount(accountId).orElseThrow();

        Course courseToUpdate = repository.findById(courseId).orElseThrow();
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setInstructor(instructor);
        if (courseToUpdate.getInstructor().equals(instructor)) {
            repository.save(courseToUpdate);
            return courseToUpdate;
        } else {
            throw new Exception("Unauthorized");
        }

    }

    @Override
    public Course archiveCourse(Long accountId, Long courseId) throws Exception {

        Instructor instructor = repository.getInstructorByAccount(accountId).orElseThrow();

        Course courseToUpdate = repository.findById(courseId).orElseThrow();
        courseToUpdate.setArchived(!courseToUpdate.getArchived());
        if (courseToUpdate.getInstructor().equals(instructor)) {
            repository.save(courseToUpdate);
            return courseToUpdate;
        } else {
            throw new Exception("Unauthorized");
        }
    }

    @Override
    public Iterable<Course> getArchivedCourses(Long accountId) {
        return repository.getArchivedCourses(accountId);
    }
}
