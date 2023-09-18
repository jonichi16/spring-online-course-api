package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.exceptions.NotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedException;
import com.jonichi.soc.models.Course;
import com.jonichi.soc.services.CourseService;
import com.jonichi.soc.utils.responses.V1.ApiResponseV1;
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
    private CourseService service;

    @GetMapping(path = "/courses")
    public ResponseEntity<ApiResponseV1> getCourses(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", required = false) Integer pageSize,
            @RequestParam(name = "sort", defaultValue = "title") String sortBy
    ) {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getCoursesV1(page, pageSize, sortBy),
                        "Success!"
                ),
                status
        );
    }

    @GetMapping(path = "/courses/{courseId}")
    public ResponseEntity<ApiResponseV1> getCourse(@PathVariable Long courseId) throws NotFoundException {

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
    public ResponseEntity<ApiResponseV1> addCourse(
            @PathVariable Long userId,
            @RequestBody Course course
    ) throws NotFoundException {
        HttpStatus status = HttpStatus.CREATED;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.addCourseV1(userId, course),
                        "Course added successfully!"
                ),
                status
        );
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PutMapping(path = "/users/{userId}/courses/{courseId}")
    public ResponseEntity<ApiResponseV1> updateCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestBody Course course
    ) throws UnauthorizedException, NotFoundException {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.updateCourseV1(userId, courseId, course),
                        "Course updated successfully!"
                ),
                status
        );

    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PutMapping(path = "/users/{userId}/courses/{courseId}/archived")
    public ResponseEntity<ApiResponseV1> archiveCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) throws UnauthorizedException, NotFoundException {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.archiveCourseV1(userId, courseId),
                        "Course archived successfully"
                ),
                status
        );

    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping(path = "/users/{userId}/archived")
    public ResponseEntity<ApiResponseV1> getArchivedCourses(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", required = false) Integer pageSize,
            @RequestParam(name = "sort", defaultValue = "title") String sortBy
    ) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getArchivedCoursesV1(userId, page, pageSize, sortBy),
                        "Success"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/courses/search")
    public ResponseEntity<ApiResponseV1> searchCourses(
            @RequestParam(name = "q") String q,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", required = false) Integer pageSize,
            @RequestParam(name = "sort", defaultValue = "title") String sortBy
    ) {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.searchCourseV1(page, pageSize, sortBy, q),
                        "Success"
                ),
                status
        );
    }

}
