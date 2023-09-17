package com.jonichi.soc.dto.V1;

import com.jonichi.soc.utils.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

public record UserDtoV1(
        Long id,
        String username,
        String fullName,
        String email,
        String imageUrl,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
