package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByInstructorIdAndIsArchivedIsTrue(Long instructorId, Pageable pageable);

    Integer countByIsArchivedIsFalse();

    Integer countByInstructorIdAndIsArchivedIsTrue(Long instructorId);

    Page<Course> findByIsArchivedIsFalse(Pageable pageable);

    @Query("SELECT c FROM Course c " +
            "WHERE c.title LIKE ?1 " +
            "OR c.description LIKE ?1 " +
            "OR c.instructor LIKE ?1")
    Page<Course> findByQuery(Pageable pageable, String query);

}
