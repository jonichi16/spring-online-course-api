package com.jonichi.soc.controllers;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(path = "/courses")
    public ResponseEntity<Object> getAllCourses() {
        return new ResponseEntity<>(
                service.getAllCourses(),
                HttpStatus.OK
        );
    }
}
