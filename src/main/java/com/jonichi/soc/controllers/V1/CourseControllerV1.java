package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.responses.V1.ApiResponseV1;
import com.jonichi.soc.responses.V1.ExceptionResponseV1;
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

        return new ResponseEntity<>(
                setApiResponse(
                        status,
                        service.getAllCoursesV1(),
                        "Success"
                ),
                status
        );
    }

    @GetMapping(path = "/courses/{courseId}")
    public ResponseEntity<ApiResponseV1> getCourse(@PathVariable Long courseId) {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                setApiResponse(
                        status,
                        service.getCourseV1(courseId),
                        "Success"
                ),
                status
        );
    }

    @PostMapping(path = "/instructors/{accountId}/courses")
    public ResponseEntity<?> addCourse(@PathVariable Long accountId, @RequestBody Course course) {
        try {
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(
                    setApiResponse(
                            status,
                            service.addCourseV1(accountId, course),
                            "Success"
                    ),
                    status
            );
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;

            return new ResponseEntity<>(
                    setExceptionResponse(
                            status,
                            e.getMessage(),
                            "Unauthorized"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PutMapping(path = "/instructors/{accountId}/courses/{courseId}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long accountId,
            @PathVariable Long courseId,
            @RequestBody Course course
    ) {
        try {
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(
                    setApiResponse(
                            status,
                            service.updateCourseV1(accountId, courseId, course),
                            "Success"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(
                    setExceptionResponse(
                            status,
                            e.getMessage(),
                            "Unauthorized"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PutMapping(path = "/instructors/{accountId}/courses/{courseId}/archived")
    public ResponseEntity<?> archiveCourse(
            @PathVariable Long accountId,
            @PathVariable Long courseId
    ) {
        try {
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(
                    setApiResponse(
                            status,
                            service.archiveCourseV1(accountId, courseId),
                            "Success"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(
                    setExceptionResponse(
                            status,
                            e.getMessage(),
                            "Unauthorized"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @GetMapping(path = "/instructors/{accountId}/archived")
    public ResponseEntity<?> getArchivedCourses(@PathVariable Long accountId) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(
                setApiResponse(
                        status,
                        service.getArchivedCoursesV1(accountId),
                        "Success"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/students/{accountId}/courses/{courseId}")
    public ResponseEntity<?> enrollCourse(
            @PathVariable Long accountId,
            @PathVariable Long courseId
    ) throws Exception {

        try {
            HttpStatus status = HttpStatus.CREATED;
            return new ResponseEntity<>(
                    setApiResponse(
                            status,
                            service.enrollCourseV1(accountId, courseId),
                            "Enrolled Successfully"
                    ),
                    status
            );
        } catch (Exception e) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            return new ResponseEntity<>(
                    setExceptionResponse(
                            status,
                            e.getMessage(),
                            "Something went wrong!"
                    ),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    private ApiResponseV1 setApiResponse(HttpStatus status, Object data, String message) {
        ApiResponseV1 response = new ApiResponseV1();
        response.setStatus(status.value());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    private ExceptionResponseV1 setExceptionResponse(HttpStatus status, Object data, String message) {
        ExceptionResponseV1 response = new ExceptionResponseV1();
        response.setStatus(status.value());
        response.setError(data);
        response.setMessage(message);
        return response;
    }

}
