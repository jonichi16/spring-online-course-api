package com.jonichi.soc.services;


import com.jonichi.soc.models.Course;

public interface CourseService {

    void addCourse(Long id, Course course);
    Iterable<Course> getAllCourses();
    Course getCourse(Long id);

}
