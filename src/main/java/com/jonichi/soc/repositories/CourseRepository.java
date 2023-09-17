package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstructorIdAndIsArchivedIsTrue(Long instructorId);

    List<Course> findByIsArchivedIsFalse();

}
