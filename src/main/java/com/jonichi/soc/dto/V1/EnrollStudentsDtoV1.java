package com.jonichi.soc.dto.V1;

import com.jonichi.soc.utils.enums.Role;

public record EnrollStudentsDtoV1(
        Long studentId,
        String fullName,
        String email,
        String imageUrl,
        Role role
) {
}
