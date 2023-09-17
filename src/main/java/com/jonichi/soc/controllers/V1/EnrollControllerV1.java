package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.services.EnrollService;
import com.jonichi.soc.utils.responses.V1.ApiResponseV1;
import com.jonichi.soc.utils.responses.V1.ExceptionResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/")
public class EnrollControllerV1 {

    @Autowired
    private final EnrollService service;

    public EnrollControllerV1(EnrollService service) {
        this.service = service;
    }

    @PostMapping(path = "/users/{studentId}/courses/{courseId}")
    public ResponseEntity<?> enrollCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {

        try {
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(
                    new ApiResponseV1(
                            status.value(),
                            service.enrollCourseV1(studentId, courseId),
                            "User enrolled successfully!"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

            return new ResponseEntity<>(
                    new ExceptionResponseV1(
                            status.value(),
                            e.getMessage(),
                            "Something went wrong!"
                    ),
                    status
            );
        }

    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping(path = "/users/{instructorId}/courses/{courseId}")
    public ResponseEntity<?> getCourseStudents(
            @PathVariable Long instructorId,
            @PathVariable Long courseId
    ) {
        try {
            HttpStatus status = HttpStatus.OK;

            return new ResponseEntity<>(
                    new ApiResponseV1(
                            status.value(),
                            service.getCourseStudentsV1(instructorId, courseId),
                            "Success!"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

            return new ResponseEntity<>(
                    new ExceptionResponseV1(
                            status.value(),
                            e.getMessage(),
                            "Something went wrong!"
                    ),
                    status
            );
        }
    }

}
