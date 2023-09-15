package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT i FROM Instructor i JOIN Account a ON a.id = i.account.id WHERE a.id = ?1")
    Optional<Instructor> getInstructorByAccount(Long id);

}
