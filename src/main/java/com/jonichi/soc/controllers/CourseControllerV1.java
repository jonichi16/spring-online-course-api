package com.jonichi.soc.controllers;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.responses.ApiResponseV1;
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
    public ResponseEntity<ApiResponseV1> getAllCourses() {

        HttpStatus status = HttpStatus.OK;

        ApiResponseV1 response = new ApiResponseV1();
        response.setStatus(status.value());
        response.setData(service.getAllCourses());
        response.setMessage("Success");

        return new ResponseEntity<>(
                response,
                status
        );
    }

    @GetMapping(path = "/courses/{courseId}")
    public ResponseEntity<Object> getCourse(@PathVariable Long courseId) {

        HttpStatus status = HttpStatus.OK;

        ApiResponseV1 response = new ApiResponseV1();
        response.setStatus(status.value());
        response.setData(service.getCourse(courseId));
        response.setMessage("Success");

        return new ResponseEntity<>(
                response,
                status
        );
    }

    @PostMapping(path = "/instructors/{accountId}/courses")
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

    @PutMapping(path = "/instructors/{accountId}/courses/{courseId}")
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

    @PutMapping(path = "/instructors/{accountId}/courses/{courseId}/archived")
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

    @GetMapping(path = "/instructors/{accountId}/archived")
    public ResponseEntity<Object> getArchivedCourses(@PathVariable Long accountId) {
        return new ResponseEntity<>(
                service.getArchivedCourses(accountId),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/students/{accountId}/courses/{courseId}")
    public ResponseEntity<Object> enrollCourse(
            @PathVariable Long accountId,
            @PathVariable Long courseId
    ) throws Exception {

        try {
            service.enrollCourse(accountId, courseId);
            return new ResponseEntity<>(
                    "Enrolled",
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

}
