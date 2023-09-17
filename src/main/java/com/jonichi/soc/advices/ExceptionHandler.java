package com.jonichi.soc.advices;

import com.jonichi.soc.exceptions.CourseNotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedCourseInstructorException;
import com.jonichi.soc.exceptions.UserNotFoundException;
import com.jonichi.soc.utils.responses.V1.ExceptionResponseV1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidInput(MethodArgumentNotValidException e) {

        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        return new ResponseEntity<>(
                new ExceptionResponseV1(
                        status.value(),
                        errorMap
                ),
                status
        );

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(
            {UserNotFoundException.class,
            CourseNotFoundException.class,
            UnauthorizedCourseInstructorException.class}
    )
    public ResponseEntity<Object> handleUserNotFound(Exception e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof UserNotFoundException || e instanceof CourseNotFoundException)
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        else if (e instanceof UnauthorizedCourseInstructorException)
            status = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(
                new ExceptionResponseV1(
                        status.value(),
                        e.getMessage()
                ),
                status
        );

    }

}
