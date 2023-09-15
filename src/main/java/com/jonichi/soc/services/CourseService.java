package com.jonichi.soc.services;


import com.jonichi.soc.models.Course;

public interface CourseService {

    void addCourse(Long id, Course course);
    Iterable<Course> getAllCourses();
    Course getCourse(Long id);
    Course updateCourse(Long accountId, Long courseId, Course course) throws Exception;
    Course archiveCourse(Long accountId, Long courseId) throws Exception;

}
