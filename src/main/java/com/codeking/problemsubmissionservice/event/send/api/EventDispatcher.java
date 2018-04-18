package com.codeking.problemsubmissionservice.event.send.api;

import com.codeking.problemsubmissionservice.event.dto.ProblemEvaluatedEvent;

public interface EventDispatcher {

    void dispatchProblemEvaluatedEvent(ProblemEvaluatedEvent problemEvaluatedEvent);
}
