package com.jonichi.soc.dto.V1;

public class CourseInstructorDtoV1 {

    private Long instructorId;
    private String email;

    public CourseInstructorDtoV1() {
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
