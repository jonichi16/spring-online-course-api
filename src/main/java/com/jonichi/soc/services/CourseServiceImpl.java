package com.jonichi.soc.services;

import com.jonichi.soc.models.*;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final CourseStudentRepository courseStudentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CourseStudentRepository courseStudentRepository) {
        this.courseRepository = courseRepository;
        this.courseStudentRepository = courseStudentRepository;
    }

    @Override
    public void addCourse(Long id, Course course) {

        Instructor instructor = courseRepository.findInstructorByAccountId(id).orElseThrow();
        course.setInstructor(instructor);

        courseRepository.save(course);
    }

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepository.getAvailableCourses();
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public Course updateCourse(Long accountId, Long courseId, Course course) throws Exception {

        Instructor instructor = courseRepository.findInstructorByAccountId(accountId).orElseThrow();

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow();
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setInstructor(instructor);
        if (courseToUpdate.getInstructor().equals(instructor)) {
            courseRepository.save(courseToUpdate);
            return courseToUpdate;
        } else {
            throw new Exception("Unauthorized");
        }

    }

    @Override
    public Course archiveCourse(Long accountId, Long courseId) throws Exception {

        Instructor instructor = courseRepository.findInstructorByAccountId(accountId).orElseThrow();

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow();
        courseToUpdate.setArchived(!courseToUpdate.getArchived());
        if (courseToUpdate.getInstructor().equals(instructor)) {
            courseRepository.save(courseToUpdate);
            return courseToUpdate;
        } else {
            throw new Exception("Unauthorized");
        }
    }

    @Override
    public Iterable<Course> getArchivedCourses(Long accountId) {
        return courseRepository.getArchivedCourses(accountId);
    }

    @Override
    public void enrollCourse(Long studentId, Long courseId) {
        Student student = courseRepository.findStudentByAccountId(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        CourseStudentKey id = new CourseStudentKey(courseId, studentId);

        CourseStudent courseStudent = new CourseStudent(id, student, course);

        courseStudentRepository.save(courseStudent);
    }
}
