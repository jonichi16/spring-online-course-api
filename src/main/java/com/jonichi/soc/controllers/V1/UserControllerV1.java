package com.jonichi.soc.controllers.V1;

import com.jonichi.soc.exceptions.NotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedException;
import com.jonichi.soc.models.User;
import com.jonichi.soc.services.UserService;
import com.jonichi.soc.utils.responses.V1.ApiResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/users")
public class UserControllerV1 {

    @Autowired
    private final UserService service;

    public UserControllerV1(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/me")
    public ResponseEntity<ApiResponseV1> getCurrentUser(
            @RequestHeader(name = "Authorization") String token
    ) throws NotFoundException {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.getCurrentUserV1(token),
                        "Success!"
                ),
                status
        );

    }

    @PostMapping
    public ResponseEntity<ApiResponseV1> registerUser(@RequestBody @Valid User user) {

        HttpStatus status = HttpStatus.CREATED;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.registerUserV1(user),
                        "User registered successfully!"
                ),
                status
        );
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<ApiResponseV1> updateUser(
            @PathVariable Long userId,
            @RequestBody User user,
            @RequestHeader(name = "Authorization") String token
    ) throws NotFoundException, UnauthorizedException {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.updateUserV1(userId, user, token),
                        "User updated successfully!"
                ),
                 status
        );

    }

    @PutMapping(path = "/{userId}/instructors")
    public ResponseEntity<ApiResponseV1> updateRole(
            @PathVariable Long userId,
            @RequestHeader(name = "Authorization") String token
    ) throws NotFoundException, UnauthorizedException {

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(
                new ApiResponseV1(
                        status.value(),
                        service.updateRoleV1(userId, token),
                        "User's role changed to INSTRUCTOR successfully!"
                ),
                status
        );
    }

}
