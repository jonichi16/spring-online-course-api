package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.UserDtoV1;
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
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // V1 of UserService
    public UserDtoV1 registerUserV1(User user) {

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        repository.save(user);

        return Mapper.mapToUserDtoV1(user);
    }

    public UserDtoV1 updateRoleV1(Long id) {

        User user = repository.findById(id).orElseThrow();
        user.setRole(Role.INSTRUCTOR);

        repository.save(user);

        return Mapper.mapToUserDtoV1(user);
    }

}
