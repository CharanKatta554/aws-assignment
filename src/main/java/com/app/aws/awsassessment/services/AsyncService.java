package com.app.aws.awsassessment.services;

import java.util.List;

import com.app.aws.awsassessment.enums.AWSServiceName;

public interface AsyncService {

    public Long triggerForAWSservices(List<AWSServiceName> servicesList);

    public Long triggerForS3Bucket(String bucketName);
}