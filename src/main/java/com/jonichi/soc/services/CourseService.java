package com.jonichi.soc.services;


import com.jonichi.soc.dto.V1.CourseDtoV1;
import com.jonichi.soc.models.Course;

import java.util.List;

public interface CourseService {

    // V1 - Version 1 of CourseService
    CourseDtoV1 addCourseV1(Long id, Course course);
    List<CourseDtoV1> getAllCoursesV1();
    CourseDtoV1 getCourseV1(Long id);
    CourseDtoV1 updateCourseV1(Long accountId, Long courseId, Course course) throws Exception;
    CourseDtoV1 archiveCourseV1(Long accountId, Long courseId) throws Exception;
    List<CourseDtoV1> getArchivedCoursesV1(Long accountId);
    CourseDtoV1 enrollCourseV1(Long studentId, Long courseId) throws Exception;

}
