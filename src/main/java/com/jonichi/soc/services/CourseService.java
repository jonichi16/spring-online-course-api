package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.dto.V1.CourseInstructorDtoV1;
import com.jonichi.soc.models.*;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.UserRepository;
import com.jonichi.soc.utils.mappers.Mapper;
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

        return Mapper.mapToCourseDtoV1(course);
    }

    public List<CourseDtoV1> getCoursesV1() {
        return Mapper.mapToCourseDtoV1List(
                courseRepository.findByIsArchivedIsFalse()
        );
    }

    public CourseDtoV1 getCourseV1(Long id) {
        Course course = courseRepository.findById(id).orElseThrow();

        return Mapper.mapToCourseDtoV1(course);
    }

    public CourseDtoV1 updateCourseV1(Long userId, Long courseId, Course course) throws Exception {

        User instructor = userRepository.findById(userId).orElseThrow();

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow();
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setImageUrl(course.getImageUrl());
        courseToUpdate.setInstructor(instructor);

        if (courseToUpdate.getInstructor().equals(instructor)) {
            courseRepository.save(courseToUpdate);
            return Mapper.mapToCourseDtoV1(courseToUpdate);
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
            return Mapper.mapToCourseDtoV1(courseToUpdate);
        } else {
            throw new Exception("Unauthorized");
        }
    }

    public List<CourseDtoV1> getArchivedCoursesV1(Long instructorId) {
        return Mapper.mapToCourseDtoV1List(
                courseRepository.findByInstructorIdAndIsArchivedIsTrue(instructorId)
        );
    }

}
