package com.jonichi.soc.utils.mappers;

import com.jonichi.soc.dto.V1.*;
import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Enroll;
import com.jonichi.soc.models.User;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mapper {

    public static UserDtoV1 mapToUserDtoV1(User user) {

        return new UserDtoV1(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getImageUrl(),
                user.getRole(),
                mapToEnrollCourseDtoV1List(user.getEnrollCourses()),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

    }

    public static EnrollStudentsDtoV1 mapToEnrollStudentDtoV1(Enroll enroll) {

        return new EnrollStudentsDtoV1(
                enroll.getStudent().getId(),
                enroll.getStudent().getFullName(),
                enroll.getStudent().getEmail(),
                enroll.getStudent().getImageUrl(),
                enroll.getStudent().getRole()
        );

    }

    public static List<EnrollStudentsDtoV1> mapToEnrollStudentDtoV1List(List<Enroll> enrolls) {

        List<EnrollStudentsDtoV1> enrollStudentsDtoV1List = new ArrayList<>();
        for (Enroll enroll : enrolls) {
            EnrollStudentsDtoV1 enrollStudentsDtoV1 = mapToEnrollStudentDtoV1(enroll);
            enrollStudentsDtoV1List.add(enrollStudentsDtoV1);
        }
        return enrollStudentsDtoV1List;

    }

    public static EnrollCourseDtoV1 mapToEnrollCourseDtoV1(Enroll enroll) {

        return new EnrollCourseDtoV1(
                enroll.getCourse().getId(),
                enroll.getCourse().getTitle(),
                enroll.getCourse().getDescription(),
                enroll.getStatus(),
                new CourseInstructorDtoV1(
                        enroll.getCourse().getInstructor().getId(),
                        enroll.getCourse().getInstructor().getEmail(),
                        enroll.getCourse().getInstructor().getImageUrl()
                )
        );

    }

    public static List<EnrollCourseDtoV1> mapToEnrollCourseDtoV1List(List<Enroll> enrolls) {

        List<EnrollCourseDtoV1> enrollCourseDtoList = new ArrayList<>();
        for (Enroll enroll : enrolls) {
            EnrollCourseDtoV1 enrollCourseDtoV1 = mapToEnrollCourseDtoV1(enroll);
            enrollCourseDtoList.add(enrollCourseDtoV1);
        }

        return enrollCourseDtoList;
    }

    public static CourseDtoV1 mapToCourseDtoV1(Course course) {

        return new CourseDtoV1(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                new CoursePriceDtoV1(
                        course.getAmount(),
                        course.getCurrencyCode()
                ),
                course.getArchived(),
                new CourseInstructorDtoV1(
                        course.getInstructor().getId(),
                        course.getInstructor().getEmail(),
                        course.getInstructor().getImageUrl()
                ),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }

    public static List<CourseDtoV1> mapToCourseDtoV1List(List<Course> courses) {
        List<CourseDtoV1> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoV1 courseDtoV1 = mapToCourseDtoV1(course);
            courseDtoList.add(courseDtoV1);
        }
        return courseDtoList;
    }

    public static Page<CourseDtoV1> mapToCourseDtoV1Page(Page<Course> courses) {
        return courses.map(Mapper::mapToCourseDtoV1);
    }

}
