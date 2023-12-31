package com.jonichi.soc.services;

import com.jonichi.soc.config.JwtToken;
import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.exceptions.NotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedException;
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
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtToken jwtToken;

    // V1 of CourseService
    public CourseDtoV1 addCourseV1(
            Long userId,
            Course course,
            String token
    ) throws NotFoundException, UnauthorizedException {

        User instructor = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Instructor not found!")
        );

        User user = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!user.equals(instructor)) {
            throw new UnauthorizedException("Unauthorized");
        }

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

    public CourseDtoV1 getCourseV1(Long id) throws NotFoundException {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );

        return Mapper.mapToCourseDtoV1(course);
    }

    public CourseDtoV1 updateCourseV1(
            Long userId,
            Long courseId,
            Course course,
            String token
    ) throws NotFoundException, UnauthorizedException {

        User instructor = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Instructor not found!")
        );

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );

        User user = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!user.equals(instructor)) {
            throw new UnauthorizedException("Unauthorized");
        }

        if (!courseToUpdate.getInstructor().equals(instructor)) {
            throw new UnauthorizedException("Unauthorized instructor!");
        }

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setImageUrl(course.getImageUrl());
        courseToUpdate.setAmount(course.getAmount());
        courseToUpdate.setCurrencyCode(course.getCurrencyCode());
        courseToUpdate.setInstructor(instructor);

        courseRepository.save(courseToUpdate);
        return Mapper.mapToCourseDtoV1(courseToUpdate);

    }

    public CourseDtoV1 archiveCourseV1(
            Long userId,
            Long courseId,
            String token
    ) throws NotFoundException, UnauthorizedException {

        User instructor = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Instructor not found!")
        );

        Course courseToUpdate = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );

        User user = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!user.equals(instructor)) {
            throw new UnauthorizedException("Unauthorized");
        }

        if (!courseToUpdate.getInstructor().equals(instructor)) {
            throw new UnauthorizedException("Unauthorized instructor!");
        }

        courseToUpdate.setArchived(!courseToUpdate.getArchived());

        courseRepository.save(courseToUpdate);
        return Mapper.mapToCourseDtoV1(courseToUpdate);
    }

    public Page<CourseDtoV1> getArchivedCoursesV1(
            Long instructorId,
            String token,
            int page,
            Integer pageSize,
            String sortBy
    ) throws UnauthorizedException, NotFoundException {

        User user = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!user.getId().equals(instructorId)) {
            throw new UnauthorizedException("Unauthorized");
        }

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

    public Page<CourseDtoV1> searchCourseV1(
            int page,
            Integer pageSize,
            String sortBy,
            String query
    ) {

        if (pageSize == null) {
            pageSize = courseRepository.countByIsArchivedIsFalse();
        }

        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        Page<Course> resultCourses = courseRepository.findByQuery(pageable, "%" + query + "%");

        return Mapper.mapToCourseDtoV1Page(resultCourses);

    }

}
