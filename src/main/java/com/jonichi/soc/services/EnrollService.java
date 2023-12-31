package com.jonichi.soc.services;

import com.jonichi.soc.config.JwtToken;
import com.jonichi.soc.dto.V1.EnrollCourseDtoV1;
import com.jonichi.soc.dto.V1.EnrollStudentsDtoV1;
import com.jonichi.soc.exceptions.*;
import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Enroll;
import com.jonichi.soc.models.User;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.EnrollRepository;
import com.jonichi.soc.repositories.UserRepository;
import com.jonichi.soc.utils.enums.Status;
import com.jonichi.soc.utils.mappers.Mapper;
import com.jonichi.soc.utils.requests.RateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EnrollService {

    @Autowired
    private EnrollRepository enrollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JwtToken jwtToken;


    public EnrollCourseDtoV1 enrollCourseV1(
            Long studentId,
            Long courseId,
            String token
    ) throws NotFoundException, InvalidEntityException, UnauthorizedException {

        User student = userRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student not found!")
        );
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );

        User user = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!user.equals(student)) {
            throw new UnauthorizedException("Unauthorized");
        }

        Enroll existingEnroll = enrollRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (existingEnroll != null) {
            throw new InvalidEntityException("User already enrolled to this course!");
        }

        if (course.getInstructor().equals(student)) {
            throw new InvalidEntityException("You own this course");
        }

        if (course.getArchived()) {
            throw new InvalidEntityException("Course is archived");
        }

        Enroll enroll = new Enroll(student, course);
        enrollRepository.save(enroll);

        return Mapper.mapToEnrollCourseDtoV1(enroll);
    }

    public List<EnrollStudentsDtoV1> getCourseStudentsV1(
            Long instructorId,
            Long courseId,
            String token
    ) throws NotFoundException, UnauthorizedException {

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );
        User instructor = userRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException("Instructor not found!")
        );

        User user = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!user.equals(instructor)) {
            throw new UnauthorizedException("Unauthorized");
        }

        if (!course.getInstructor().equals(instructor)) {
            throw new UnauthorizedException("Unauthorized instructor!");
        }

        List<Enroll> enrolls = enrollRepository.findByCourseId(courseId);
        return Mapper.mapToEnrollStudentDtoV1List(enrolls);
    }

    public List<EnrollCourseDtoV1> getEnrolledCoursesV1(
            Long userId,
            String token
    ) throws NotFoundException, UnauthorizedException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        User auth = userRepository.findByUsername(jwtToken.getUsernameFromToken(token)).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!auth.equals(user)) {
            throw new UnauthorizedException("Unauthorized");
        }

        List<Enroll> enrolls = enrollRepository.findByStudentId(userId);

        return Mapper.mapToEnrollCourseDtoV1List(enrolls);

    }

    public EnrollCourseDtoV1 updateCourseStatusV1(
            Long userId,
            Long courseId
    ) throws NotFoundException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );

        Enroll enrollToUpdate = enrollRepository.findByStudentIdAndCourseId(userId, courseId);

        Status status =  enrollToUpdate.getStatus();

        if (status.equals(Status.PENDING))
            status = Status.ON_GOING;
        else if (status.equals(Status.ON_GOING))
            status = Status.COMPLETED;

        enrollToUpdate.setStatus(status);

        enrollRepository.save(enrollToUpdate);

        return Mapper.mapToEnrollCourseDtoV1(enrollToUpdate);
    }

    public EnrollCourseDtoV1 rateCourseV1(
            Long userId,
            Long courseId,
            RateRequest rateRequest
    ) throws NotFoundException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found!")
        );

        Enroll enrollToUpdate = enrollRepository.findByStudentIdAndCourseId(userId, courseId);

        enrollToUpdate.setRating(rateRequest.getRating());
        enrollToUpdate.setComment(rateRequest.getComment());

        enrollRepository.save(enrollToUpdate);

        return Mapper.mapToEnrollCourseDtoV1(enrollToUpdate);

    }

}
