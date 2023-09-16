package com.jonichi.soc.repositories;

import com.jonichi.soc.models.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    Enroll findByStudentIdAndCourseId(Long studentId, Long courseId);

}
