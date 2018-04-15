package com.codeking.problemsubmissionservice.repository;

import com.codeking.problemsubmissionservice.domain.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * crud repository on Problem Entity.
 * @see Problem
 */
@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {
}
