package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.dto.V1.CourseInstructorDtoV1;
import com.jonichi.soc.models.*;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    // V1 - Version 1 of CourseServiceImpl
    @Override
    public CourseDtoV1 addCourseV1(Long id, Course course) {

        Instructor instructor = courseRepository.findInstructorByAccountId(id).orElseThrow();
        course.setInstructor(instructor);

        courseRepository.save(course);

        return mapToCourseDtoV1(course);
    }

    @Override
    public List<CourseDtoV1> getAllCoursesV1() {
        return mapToCourseDtoV1List(courseRepository.getAvailableCourses());
    }

    @Override
    public CourseDtoV1 getCourseV1(Long id) {
        Course course = courseRepository.findById(id).orElseThrow();

        return mapToCourseDtoV1(course);
    }

    @Override
    public CourseDtoV1 updateCourseV1(Long accountId, Long courseId, Course course) throws Exception {

        Instructor instructor = courseRepository.findInstructorByAccountId(accountId).orElseThrow();

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow();
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setInstructor(instructor);
        if (courseToUpdate.getInstructor().equals(instructor)) {
            courseRepository.save(courseToUpdate);
            return mapToCourseDtoV1(courseToUpdate);
        } else {
            throw new Exception("Unauthorized");
        }

    }

    @Override
    public CourseDtoV1 archiveCourseV1(Long accountId, Long courseId) throws Exception {

        Instructor instructor = courseRepository.findInstructorByAccountId(accountId).orElseThrow();

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow();
        courseToUpdate.setArchived(!courseToUpdate.getArchived());
        if (courseToUpdate.getInstructor().equals(instructor)) {
            courseRepository.save(courseToUpdate);
            return mapToCourseDtoV1(courseToUpdate);
        } else {
            throw new Exception("Unauthorized");
        }
    }

    @Override
    public List<CourseDtoV1> getArchivedCoursesV1(Long accountId) {
        return mapToCourseDtoV1List(courseRepository.getArchivedCourses(accountId));
    }

    @Override
    public CourseDtoV1 enrollCourseV1(Long studentId, Long courseId) throws Exception {
        Student student = courseRepository.findStudentByAccountId(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        CourseStudentKey id = new CourseStudentKey(courseId, studentId);

        if (!course.getArchived()) {
            CourseStudent courseStudent = new CourseStudent(id, student, course);
            courseStudentRepository.save(courseStudent);
            return mapToCourseDtoV1(course);
        } else {
            throw new Exception("Course is archived!");
        }
    }

    private List<CourseDtoV1> mapToCourseDtoV1List(List<Course> courses) {
        List<CourseDtoV1> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseInstructorDtoV1 instructorDtoV1 = new CourseInstructorDtoV1();
            instructorDtoV1.setInstructorId(course.getInstructor().getId());
            instructorDtoV1.setEmail(course.getInstructor().getEmail());

            CourseDtoV1 courseDtoV1 = new CourseDtoV1();
            courseDtoV1.setId(course.getId());
            courseDtoV1.setTitle(course.getTitle());
            courseDtoV1.setDescription(course.getDescription());
            courseDtoV1.setArchived(course.getArchived());
            courseDtoV1.setInstructor(instructorDtoV1);
            courseDtoV1.setCreatedAt(course.getCreatedAt());
            courseDtoV1.setUpdatedAt(course.getUpdatedAt());
            courseDtoList.add(courseDtoV1);
        }
        return courseDtoList;
    }

    private CourseDtoV1 mapToCourseDtoV1(Course course) {
        CourseInstructorDtoV1 instructorDtoV1 = new CourseInstructorDtoV1();
        instructorDtoV1.setInstructorId(course.getInstructor().getId());
        instructorDtoV1.setEmail(course.getInstructor().getEmail());

        CourseDtoV1 courseDtoV1 = new CourseDtoV1();
        courseDtoV1.setId(course.getId());
        courseDtoV1.setTitle(course.getTitle());
        courseDtoV1.setDescription(course.getDescription());
        courseDtoV1.setArchived(course.getArchived());
        courseDtoV1.setInstructor(instructorDtoV1);
        courseDtoV1.setCreatedAt(course.getCreatedAt());
        courseDtoV1.setUpdatedAt(course.getUpdatedAt());

        return courseDtoV1;
    }
}
