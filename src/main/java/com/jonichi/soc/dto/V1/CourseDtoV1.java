package com.jonichi.soc.dto.V1;

import com.jonichi.soc.models.Instructor;

import java.time.LocalDateTime;

public class CourseDtoV1 {

    private Long id;
    private String title;
    private String description;
    private Boolean isArchived;
    private CourseInstructorDtoV1 instructor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseDtoV1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public CourseInstructorDtoV1 getInstructor() {
        return instructor;
    }

    public void setInstructor(CourseInstructorDtoV1 instructor) {
        this.instructor = instructor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
