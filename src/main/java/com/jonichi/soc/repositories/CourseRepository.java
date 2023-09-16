package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Course;
import com.jonichi.soc.models.Instructor;
import com.jonichi.soc.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT i FROM Instructor i JOIN Account a ON a.id = i.account.id WHERE a.id = ?1")
    Optional<Instructor> findInstructorByAccountId(Long accountId);

    @Query("SELECT s FROM Student s JOIN Account a ON a.id = s.account.id WHERE a.id = ?1")
    Optional<Student> findStudentByAccountId(Long accountId);

    @Query("SELECT c FROM Course c WHERE c.instructor.account.id = ?1 AND archived = 1")
    List<Course> getArchivedCourses(Long accountId);

    @Query("SELECT c FROM Course c WHERE archived = 0")
    List<Course> getAvailableCourses();


}
