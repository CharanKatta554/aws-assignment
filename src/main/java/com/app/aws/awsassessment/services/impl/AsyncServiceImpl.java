
package com.app.aws.awsassessment.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.enums.AWSServiceName;
import com.app.aws.awsassessment.enums.JobStatus;
import com.app.aws.awsassessment.repository.JobRepository;
import com.app.aws.awsassessment.services.AWSService;
import com.app.aws.awsassessment.services.AsyncService;
import com.app.aws.awsassessment.services.JobService;



@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private JobService jobServiceImpl;

    @Autowired
    private AWSService awsServiceImpl;

    public Long triggerForAWSservices(List<AWSServiceName> servicesList) {
        Job job = jobServiceImpl.create();
        awsServiceImpl.asyncFetchAll(servicesList, job);
        return job.getId();
    }


    public Long triggerForS3Bucket(String bucketName) {
        Job job = jobServiceImpl.create();
        awsServiceImpl.asyncGetS3BucketObjects(bucketName,job);
        return job.getId();
    }
}

