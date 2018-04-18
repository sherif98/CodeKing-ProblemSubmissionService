package com.codeking.problemsubmissionservice.event.receive;

import com.codeking.problemsubmissionservice.domain.Problem;
import com.codeking.problemsubmissionservice.event.dto.ProblemCreatedEvent;
import com.codeking.problemsubmissionservice.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ProblemCreatedSink.class)
public class ProblemCreatedEventListener {

    @Autowired
    private ProblemRepository problemRepository;


    @StreamListener(ProblemCreatedSink.INPUT)
    public void handleProblemCreatedEvent(ProblemCreatedEvent problemCreatedEvent) {
        Problem problem = Problem.builder()
                .problemId(problemCreatedEvent.getProblemId())
                .problemTitle(problemCreatedEvent.getProblemTitle())
                .timeLimit(problemCreatedEvent.getTimeLimit())
                .testCases(problemCreatedEvent.getTestCases())
                .build();
        problemRepository.save(problem);
    }
}
