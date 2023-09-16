package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.StudentDtoV1;
import com.jonichi.soc.requests.StudentRequest;

public interface StudentService {

    StudentDtoV1 registerStudentV1(StudentRequest student);

}
