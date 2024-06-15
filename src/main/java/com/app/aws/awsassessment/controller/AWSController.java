package com.app.aws.awsassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.entites.S3ObjectEntity;
import com.app.aws.awsassessment.enums.AWSServiceName;
import com.app.aws.awsassessment.services.AWSService;
import com.app.aws.awsassessment.services.AsyncService;
import com.app.aws.awsassessment.services.JobService;

import java.util.List;

@RestController
@RequestMapping("/aws")
public class AWSController {

    @Autowired
    private AsyncService asyncServiceImpl;

    @Autowired
    private JobService jobServiceImpl;

    @Autowired
    private AWSService awsServiceImpl;

    @PostMapping("/discover")
    public String discoverServices(@RequestBody List<AWSServiceName> services) {
        return asyncServiceImpl.triggerForAWSservices(services).toString();
    }

    @GetMapping("/discover")
    public List<String> getDiscoveryResult(@RequestParam AWSServiceName serviceName) {
        return awsServiceImpl.getDiscoveryResult(serviceName);
    }

    @GetMapping("/job/{jobId}")
    public Job getJob(@PathVariable Long jobId) {
        return jobServiceImpl.getBy(jobId);
    }

    @PostMapping("/s3/{bucketName}")
    public String getS3BucketObjects(@PathVariable String bucketName) {
        return asyncServiceImpl.triggerForS3Bucket(bucketName).toString();
    }

    @GetMapping("/s3/{bucketName}")
    public List<S3ObjectEntity> getS3BucketObjectLike(@PathVariable String bucketName,
            @RequestParam String filePattern) {
        return awsServiceImpl.getS3BucketObjectLike(bucketName, filePattern);
    }

    @GetMapping("/s3/{bucketName}/count")
    public long getS3BucketObjectCount(@PathVariable String bucketName) {
        return awsServiceImpl.getS3BucketObjectsCount(bucketName);
    }
}
