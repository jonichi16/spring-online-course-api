package com.jonichi.soc.services;

import com.jonichi.soc.config.JwtToken;
import com.jonichi.soc.dto.V1.UserDtoV1;
import com.jonichi.soc.exceptions.NotFoundException;
import com.jonichi.soc.exceptions.UnauthorizedException;
import com.jonichi.soc.models.User;
import com.jonichi.soc.repositories.UserRepository;
import com.jonichi.soc.utils.enums.Role;
import com.jonichi.soc.utils.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtToken jwtToken;

    // V1 of UserService
    public UserDtoV1 registerUserV1(User user) {

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        repository.save(user);

        return Mapper.mapToUserDtoV1(user);
    }

    public UserDtoV1 updateUserV1(
            Long id,
            User user,
            String token
    ) throws NotFoundException, UnauthorizedException {

        User userToUpdate = repository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
//
//        if (!userToUpdate.getUsername().equals(jwtToken.getUsernameFromToken(token))) {
//            throw new UnauthorizedException("Unauthorized");
//        }

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setImageUrl(user.getImageUrl());
        userToUpdate.setFullName(user.getFullName());
        userToUpdate.setEmail(user.getEmail());

        repository.save(userToUpdate);

        return Mapper.mapToUserDtoV1(userToUpdate);
    }

    public UserDtoV1 getCurrentUserV1(String token) throws NotFoundException {

        String username = jwtToken.getUsernameFromToken(token);

        User user = repository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        return Mapper.mapToUserDtoV1(user);

    }

    public UserDtoV1 updateRoleV1(Long id) throws NotFoundException {

        User user = repository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        user.setRole(Role.INSTRUCTOR);

        repository.save(user);

        return Mapper.mapToUserDtoV1(user);
    }

}
