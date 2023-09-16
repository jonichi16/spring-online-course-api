package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.instructor.id = ?1 AND archived = 1")
    List<Course> getArchivedCourses(Long accountId);

    @Query("SELECT c FROM Course c WHERE archived = 0")
    List<Course> getAvailableCourses();


}
