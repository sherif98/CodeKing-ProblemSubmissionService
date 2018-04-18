package com.codeking.problemsubmissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProblemSubmissionServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProblemSubmissionServiceApplication.class, args);
    }

}
