package com.app.aws.awsassessment.services;

import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.enums.JobStatus;

public interface JobService {
    
    public Job getBy(Long jobId);

     Job create();

     void updateStatus(Long jobId, JobStatus status);
}
