package com.app.aws.awsassessment.services;

import java.util.List;

import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.entites.S3ObjectEntity;
import com.app.aws.awsassessment.enums.AWSServiceName;

public interface AWSService {
    
    List<String> getDiscoveryResult(AWSServiceName service);

    public long getS3BucketObjectsCount(String bucketName);

    public List<S3ObjectEntity> getS3BucketObjectLike(String bucketName, String pattern);

    public void asyncFetchAll(List<AWSServiceName> services, Job job);

    public void asyncGetS3BucketObjects(String bucketName, Job job);

}
