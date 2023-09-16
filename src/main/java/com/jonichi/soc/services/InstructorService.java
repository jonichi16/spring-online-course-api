package com.jonichi.soc.services;

import com.jonichi.soc.dto.V1.InstructorDtoV1;
import com.jonichi.soc.requests.InstructorRequest;

public interface InstructorService {

    InstructorDtoV1 registerInstructorV1(InstructorRequest instructor);
}
