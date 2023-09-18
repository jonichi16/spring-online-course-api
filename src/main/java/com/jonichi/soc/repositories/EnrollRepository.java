package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    Enroll findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enroll> findByCourseId(Long courseId);

    List<Enroll> findByStudentId(Long studentId);

}
