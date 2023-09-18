package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.dto.V1.EnrollCourseDtoV1;
import com.jonichi.soc.exceptions.*;
import com.jonichi.soc.services.EnrollService;
import com.jonichi.soc.utils.responses.V1.ApiResponseV1;
import com.jonichi.soc.utils.responses.V1.ExceptionResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<ApiResponseV1> enrollCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) throws NotFoundException, InvalidEntityException {
        HttpStatus status = HttpStatus.CREATED;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.enrollCourseV1(studentId, courseId),
                        "User enrolled successfully!"
                ),
                status
        );

    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping(path = "/users/{instructorId}/courses/{courseId}")
    public ResponseEntity<ApiResponseV1> getCourseStudents(
            @PathVariable Long instructorId,
            @PathVariable Long courseId
    ) throws UnauthorizedException, NotFoundException {
        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getCourseStudentsV1(instructorId, courseId),
                        "Success!"
                ),
                status
        );
    }

    @GetMapping(path = "/users/{userId}/enrolled-courses")
    public ResponseEntity<ApiResponseV1> getEnrolledStudents(
            @PathVariable Long userId
    ) throws NotFoundException {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getEnrolledCoursesV1(userId),
                        "Success!"
                ),
                status
        );

    }

    @PutMapping(path = "/users/{userId}/courses/{courseId}/status")
    public ResponseEntity<ApiResponseV1> updateCourseStatus(
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) throws NotFoundException {

        HttpStatus status = HttpStatus.OK;

        EnrollCourseDtoV1 course = service.updateCourseStatusV1(userId, courseId);

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        course,
                        "Course status: " + course.status()
                ),
                status
        );

    }

}
