package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.dto.V1.CourseInstructorDtoV1;
import com.jonichi.soc.models.*;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.UserRepository;
import com.jonichi.soc.utils.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // V1 of CourseService
    public CourseDtoV1 addCourseV1(Long userId, Course course) throws Exception {

        User instructor = userRepository.findById(userId).orElseThrow();

        course.setInstructor(instructor);
        courseRepository.save(course);

        return mapToCourseDtoV1(course);
    }

    public List<CourseDtoV1> getCoursesV1() {
        return mapToCourseDtoV1List(courseRepository.findByIsArchivedIsFalse());
    }

    public CourseDtoV1 getCourseV1(Long id) {
        Course course = courseRepository.findById(id).orElseThrow();

        return mapToCourseDtoV1(course);
    }

    public CourseDtoV1 updateCourseV1(Long userId, Long courseId, Course course) throws Exception {

        User instructor = userRepository.findById(userId).orElseThrow();

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

    public CourseDtoV1 archiveCourseV1(Long userId, Long courseId) throws Exception {

        User instructor = userRepository.findById(userId).orElseThrow();

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow();
        courseToUpdate.setArchived(!courseToUpdate.getArchived());

        if (courseToUpdate.getInstructor().equals(instructor)) {
            courseRepository.save(courseToUpdate);
            return mapToCourseDtoV1(courseToUpdate);
        } else {
            throw new Exception("Unauthorized");
        }
    }

    public List<CourseDtoV1> getArchivedCoursesV1(Long instructorId) {
        return mapToCourseDtoV1List(courseRepository.findByInstructorIdAndIsArchivedIsTrue(instructorId));
    }

    private List<CourseDtoV1> mapToCourseDtoV1List(List<Course> courses) {
        List<CourseDtoV1> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoV1 courseDtoV1 = mapToCourseDtoV1(course);
            courseDtoList.add(courseDtoV1);
        }
        return courseDtoList;
    }

    private CourseDtoV1 mapToCourseDtoV1(Course course) {
        CourseInstructorDtoV1 instructorDtoV1 = new CourseInstructorDtoV1();
        instructorDtoV1.setInstructorId(course.getInstructor().getId());
        instructorDtoV1.setEmail(course.getInstructor().getEmail());
        instructorDtoV1.setInstructorImgUrl(course.getInstructor().getImageUrl());

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
