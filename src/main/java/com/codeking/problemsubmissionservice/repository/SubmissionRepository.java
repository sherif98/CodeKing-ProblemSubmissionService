package com.codeking.problemsubmissionservice.repository;

import com.codeking.problemsubmissionservice.domain.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * crud repository on Submission Entity.
 *
 * @see Submission
 */
@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {

    List<Submission> findAllByUserId(String userId);
}
