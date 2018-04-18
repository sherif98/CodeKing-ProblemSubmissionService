package com.codeking.problemsubmissionservice.event.impl;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProblemEvaluatedSource {

    @Output
    MessageChannel dispatcher();
}
