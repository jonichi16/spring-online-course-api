package com.jonichi.soc.advices;

import com.jonichi.soc.exceptions.InvalidEntityException;
import com.jonichi.soc.exceptions.NotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedException;
import com.jonichi.soc.utils.responses.V1.ExceptionResponseV1;
import org.springframework.dao.DataIntegrityViolationException;
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

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDatabaseException(DataIntegrityViolationException e) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        return new ResponseEntity<>(
                new ExceptionResponseV1(
                        status.value(),
                        e.getCause().getCause().getMessage()
                ),
                status
        );

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(
            {InvalidEntityException.class,
            NotFoundException.class,
            UnauthorizedException.class}
    )
    public ResponseEntity<Object> handleApiException(Exception e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof InvalidEntityException)
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        else if (e instanceof NotFoundException)
            status = HttpStatus.NOT_FOUND;
        else if (e instanceof UnauthorizedException)
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
