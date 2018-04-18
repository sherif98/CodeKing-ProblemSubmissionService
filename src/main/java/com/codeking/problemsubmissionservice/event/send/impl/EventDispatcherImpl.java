package com.codeking.problemsubmissionservice.event.send.impl;


import com.codeking.problemsubmissionservice.event.send.api.EventDispatcher;
import com.codeking.problemsubmissionservice.event.dto.ProblemEvaluatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@EnableBinding(ProblemEvaluatedSource.class)
public class EventDispatcherImpl implements EventDispatcher {

    @Autowired
    private ProblemEvaluatedSource problemEvaluatedSource;

    @Override
    public void dispatchProblemEvaluatedEvent(ProblemEvaluatedEvent problemEvaluatedEvent) {
        Message<ProblemEvaluatedEvent> problemEvaluatedEventMessage = MessageBuilder.withPayload(problemEvaluatedEvent).build();
        problemEvaluatedSource.dispatcher().send(problemEvaluatedEventMessage);
    }
}
