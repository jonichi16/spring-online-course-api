package com.jonichi.soc.dto.V1;

import java.time.LocalDateTime;

public record CourseDtoV1(
        Long id,
        String title,
        String description,
        CoursePriceDtoV1 price,
        boolean isArchived,
        CourseInstructorDtoV1 instructor,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
