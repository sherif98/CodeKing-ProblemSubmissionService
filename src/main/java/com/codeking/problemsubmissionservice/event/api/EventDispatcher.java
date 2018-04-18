package com.codeking.problemsubmissionservice.event.api;

import com.codeking.problemsubmissionservice.event.dto.ProblemEvaluatedEvent;

public interface EventDispatcher {

    void dispatchProblemEvaluatedEvent(ProblemEvaluatedEvent problemEvaluatedEvent);
}
