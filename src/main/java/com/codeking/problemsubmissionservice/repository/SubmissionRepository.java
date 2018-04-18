package com.codeking.problemsubmissionservice.repository;

import com.codeking.problemsubmissionservice.domain.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * crud repository on Submission Entity.
 *
 * @see Submission
 */
@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {

    Page<Submission> findAllByUserId(String userId, Pageable pageable);
}
