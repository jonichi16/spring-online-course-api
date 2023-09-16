package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.utils.responses.V1.ApiResponseV1;
import com.jonichi.soc.utils.responses.V1.ExceptionResponseV1;
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

    @GetMapping(path = "/courses")
    public ResponseEntity<ApiResponseV1> getCourses() {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getCoursesV1(),
                        "Success!"
                ),
                status
        );
    }

    @GetMapping(path = "/courses/{courseId}")
    public ResponseEntity<ApiResponseV1> getCourse(@PathVariable Long courseId) {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getCourseV1(courseId),
                        "Success!"
                ),
                status
        );
    }


    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping(path = "/users/{userId}/courses")
    public ResponseEntity<?> addCourse(@PathVariable Long userId, @RequestBody Course course) {
        try {
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(
                    new ApiResponseV1(
                            status.value(),
                            service.addCourseV1(userId, course),
                            "Course added successfully!"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;

            return new ResponseEntity<>(
                    new ExceptionResponseV1(
                            status.value(),
                            e.getMessage(),
                            "Unauthorized"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PutMapping(path = "/users/{userId}/courses/{courseId}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestBody Course course
    ) {
        try {
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(
                    new ApiResponseV1(
                            status.value(),
                            service.updateCourseV1(userId, courseId, course),
                            "Course updated successfully!"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(
                    new ExceptionResponseV1(
                            status.value(),
                            e.getMessage(),
                            "Unauthorized"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PutMapping(path = "/users/{userId}/courses/{courseId}/archived")
    public ResponseEntity<?> archiveCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) {
        try {
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(
                    new ApiResponseV1(
                            status.value(),
                            service.archiveCourseV1(userId, courseId),
                            "Course archived successfully"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(
                    new ExceptionResponseV1(
                            status.value(),
                            e.getMessage(),
                            "Unauthorized"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping(path = "/users/{userId}/archived")
    public ResponseEntity<?> getArchivedCourses(@PathVariable Long userId) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getArchivedCoursesV1(userId),
                        "Success"
                ),
                HttpStatus.OK
        );
    }

}
