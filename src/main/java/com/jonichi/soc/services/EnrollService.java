package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.CourseInstructorDtoV1;
import com.jonichi.soc.dto.V1.EnrollCourseDtoV1;
import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Enroll;
import com.jonichi.soc.models.User;
import com.jonichi.soc.repositories.CourseRepository;
import com.jonichi.soc.repositories.EnrollRepository;
import com.jonichi.soc.repositories.UserRepository;
import com.jonichi.soc.utils.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollService {

    @Autowired
    private final EnrollRepository enrollRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseRepository courseRepository;


    public EnrollService(
            EnrollRepository enrollRepository,
            UserRepository userRepository,
            CourseRepository courseRepository
    ) {
        this.enrollRepository = enrollRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public EnrollCourseDtoV1 enrollCourseV1(Long studentId, Long courseId) throws Exception {

        User student = userRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        Enroll existingEnroll = enrollRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (existingEnroll != null) {
            throw new Exception("User already enrolled to this course!");
        }

        if (course.getInstructor().equals(student)) {
            throw new Exception("You own this course");
        }

        if (course.getArchived()) {
            throw new Exception("Course is archived");
        }

        Enroll enroll = new Enroll(student, course);
        enrollRepository.save(enroll);

        return Mapper.mapToEnrollCourseDtoV1(enroll);
    }

}
