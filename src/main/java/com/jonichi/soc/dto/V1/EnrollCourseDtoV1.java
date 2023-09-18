package com.jonichi.soc.dto.V1;

import com.jonichi.soc.utils.enums.Status;

import java.time.LocalDateTime;

public record EnrollCourseDtoV1(
        Long courseId,
        String title,
        String description,
        Status status,
        CourseInstructorDtoV1 instructor,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
