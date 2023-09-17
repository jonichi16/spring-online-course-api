package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.exceptions.CourseNotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedCourseInstructorException;
import com.jonichi.soc.exceptions.UserNotFoundException;
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
    public CourseDtoV1 addCourseV1(Long userId, Course course) throws UserNotFoundException {

        User instructor = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Instructor not found!")
        );

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

    public CourseDtoV1 getCourseV1(Long id) throws CourseNotFoundException {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundException("Course not found!")
        );

        return Mapper.mapToCourseDtoV1(course);
    }

    public CourseDtoV1 updateCourseV1(
            Long userId,
            Long courseId,
            Course course
    ) throws UserNotFoundException, CourseNotFoundException, UnauthorizedCourseInstructorException {

        User instructor = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Instructor not found!")
        );

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException("Course not found!")
        );

        if (!courseToUpdate.getInstructor().equals(instructor)) {
            throw new UnauthorizedCourseInstructorException("Unauthorized instructor!");
        }

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setImageUrl(course.getImageUrl());
        courseToUpdate.setInstructor(instructor);

        courseRepository.save(courseToUpdate);
        return Mapper.mapToCourseDtoV1(courseToUpdate);

    }

    public CourseDtoV1 archiveCourseV1(
            Long userId,
            Long courseId
    ) throws UserNotFoundException, CourseNotFoundException, UnauthorizedCourseInstructorException {

        User instructor = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Instructor not found!")
        );

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException("Course not found!")
        );

        if (!courseToUpdate.getInstructor().equals(instructor)) {
            throw new UnauthorizedCourseInstructorException("Unauthorized instructor!");
        }

        courseToUpdate.setArchived(!courseToUpdate.getArchived());

        courseRepository.save(courseToUpdate);
        return Mapper.mapToCourseDtoV1(courseToUpdate);
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
