package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.User;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.UserRepository;
import com.jonichi.soc.utils.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<CourseDtoV1> getCoursesV1(int page, Integer pageSize, String sortBy) {

        if (pageSize == null) {
            pageSize = courseRepository.countByIsArchivedIsFalse();
        }

        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        Page<Course> courses = courseRepository.findByIsArchivedIsFalse(pageable);

        return Mapper.mapToCourseDtoV1Page(courses);
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

    public Page<CourseDtoV1> getArchivedCoursesV1(
            Long instructorId,
            int page,
            Integer pageSize,
            String sortBy
    ) {

        if (pageSize == null) {
            pageSize = courseRepository.countByInstructorIdAndIsArchivedIsTrue(instructorId);
        }

        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        Page<Course> archivedCourses = courseRepository.findByInstructorIdAndIsArchivedIsTrue(
                instructorId,
                pageable
        );

        return Mapper.mapToCourseDtoV1Page(archivedCourses);
    }

}
