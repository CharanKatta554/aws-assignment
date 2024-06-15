package com.app.aws.awsassessment.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.enums.JobStatus;
import com.app.aws.awsassessment.repository.JobRepository;
import com.app.aws.awsassessment.services.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job getBy(Long jobId) {
        return jobRepository.findOneById(jobId);
    }

    @Override
    public Job create() {
        Job job = new Job();
        job.setStartTime(LocalDateTime.now());
        job.setStatus(JobStatus.IN_PROGRESS);
        return jobRepository.save(job);
    }

    @Override
    public void updateStatus(Long jobId, JobStatus status) {
        jobRepository.updateJobStatusById(jobId, status);
    }
}
