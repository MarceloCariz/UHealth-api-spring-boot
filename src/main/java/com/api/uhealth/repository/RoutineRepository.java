package com.api.uhealth.repository;

import com.api.uhealth.collections.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RoutineRepository extends MongoRepository<Routine, String> {

    List<Routine> findRoutinesByDate(Date date);

}
