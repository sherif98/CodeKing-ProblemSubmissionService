package com.codeking.problemsubmissionservice.repository;

import com.codeking.problemsubmissionservice.domain.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
}
