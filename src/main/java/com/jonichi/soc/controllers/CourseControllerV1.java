package com.jonichi.soc.controllers;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1")
public class CourseControllerV1 {

    @Autowired
    private final CourseService service;

    public CourseControllerV1(CourseService service) {
        this.service = service;
    }

    @GetMapping(path = "/courses")
    public ResponseEntity<Object> getAllCourses() {
        return new ResponseEntity<>(
                service.getAllCourses(),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/courses/{courseId}")
    public ResponseEntity<Object> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(
                service.getCourse(courseId),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/accounts/{accountId}/courses")
    public ResponseEntity<Object> addCourse(@PathVariable Long accountId, @RequestBody Course course) {

        try {
            service.addCourse(accountId, course);

            return new ResponseEntity<>(
                    course,
                    HttpStatus.CREATED
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    "Unauthorized",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PutMapping(path = "/accounts/{accountId}/courses/{courseId}")
    public ResponseEntity<Object> updateCourse(
            @PathVariable Long accountId,
            @PathVariable Long courseId,
            @RequestBody Course course
    ) {
        try {
            return new ResponseEntity<>(
                    service.updateCourse(accountId, courseId, course),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Unauthorized",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PutMapping(path = "/accounts/{accountId}/courses/{courseId}/archived")
    public ResponseEntity<Object> archiveCourse(
            @PathVariable Long accountId,
            @PathVariable Long courseId
    ) {
        try {
            return new ResponseEntity<>(
                    service.archiveCourse(accountId, courseId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Unauthorized",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @GetMapping(path = "/accounts/{accountId}/archived")
    public ResponseEntity<Object> getArchivedCourses(@PathVariable Long accountId) {
        return new ResponseEntity<>(
                service.getArchivedCourses(accountId),
                HttpStatus.OK
        );
    }

}
