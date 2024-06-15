package com.app.aws.awsassessment.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import com.app.aws.awsassessment.entites.EC2Instance;
import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.entites.S3Bucket;
import com.app.aws.awsassessment.entites.S3ObjectEntity;
import com.app.aws.awsassessment.enums.AWSServiceName;
import com.app.aws.awsassessment.enums.JobStatus;
import com.app.aws.awsassessment.repository.EC2InstanceRepository;
import com.app.aws.awsassessment.repository.JobRepository;
import com.app.aws.awsassessment.repository.S3BucketRepository;
import com.app.aws.awsassessment.repository.S3ObjectRepository;
import com.app.aws.awsassessment.services.AWSService;
import com.app.aws.awsassessment.services.JobService;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AWSServiceImpl implements AWSService {

    @Autowired
    private S3Client s3Client;
    @Autowired
    private Ec2Client ec2Client;

    @Autowired
    private JobService jobServiceImpl;

    @Autowired
    private S3ObjectRepository s3ObjectRepository;

    @Autowired
    private S3BucketRepository s3BucketRepository;

    @Autowired
    private EC2InstanceRepository ec2InstanceRepository;

    private static final String DEFAULT_REGION = "ap_south_1";

    public List<String> getDiscoveryResult(AWSServiceName service) {
        switch (service) {
            case EC2:
                return ec2InstanceRepository.findAllNames();
            case S3:
                return s3BucketRepository.findAllNames();
            default:
                throw new IllegalArgumentException("Service not recognized: " + service);
        }
    }

    public long getS3BucketObjectsCount(String bucketName) {
        return s3ObjectRepository.countByBucketName(bucketName);
    }

    public List<S3ObjectEntity> getS3BucketObjectLike(String bucketName, String pattern) {
        return s3ObjectRepository.findByBucketNameAndFileNameContains(bucketName, pattern);
    }

    // Run job in background when some one calls
    // `http://localhost:8080/aws/discover` api
    public void asyncFetchAll(List<AWSServiceName> services, Job job) {
        new Thread(() -> {
            try {
                for (AWSServiceName service : services) {
                    switch (service) {
                        case EC2:
                            fetchEc2Instances(job);
                            break;
                        case S3:
                            fetchS3Buckets(job);
                            break;
                        default:
                            System.out.println("Service not recognized: " + service);
                    }
                }
                jobServiceImpl.updateStatus(job.getId(), JobStatus.SUCCESS);
            } catch (Exception e) {
                jobServiceImpl.updateStatus(job.getId(), JobStatus.FAILED);
            }
        }).start();
    }

    // Run job in background when some one calls
    // `http://localhost:8080/aws/s3/{bucketName}` api
    public void asyncGetS3BucketObjects(String bucketName, Job job) {
        new Thread(() -> {
            try {
                ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();

                ListObjectsV2Response response = s3Client.listObjectsV2(request);
                List<S3Object> s3Objects = response.contents();
                List<S3ObjectEntity> entities = new ArrayList<>();
                s3Objects.forEach(s3Object -> {
                    S3ObjectEntity entity = new S3ObjectEntity();
                    entity.setJob(job);
                    entity.setBucketName(bucketName);
                    entity.setFileName(s3Object.key());
                    entities.add(entity);
                });
                s3ObjectRepository.saveAll(entities);
                jobServiceImpl.updateStatus(job.getId(), JobStatus.SUCCESS);
            } catch (Exception e) {
                jobServiceImpl.updateStatus(job.getId(), JobStatus.FAILED);
            }
        }).start();
    }

    private void fetchEc2Instances(Job job) {
        List<String> instanceIds = ec2Client.describeInstances().reservations().stream()
                .flatMap(reservation -> reservation.instances().stream())
                .map(instance -> instance.instanceId())
                .collect(Collectors.toList());
        List<EC2Instance> ec2Instances = new ArrayList<>();
        for (String instanceId : instanceIds) {
            EC2Instance ec2Instance = new EC2Instance();
            ec2Instance.setInstanceId(instanceId);
            ec2Instance.setRegion(DEFAULT_REGION);
            ec2Instance.setJob(job);
            ec2Instances.add(ec2Instance);
        }
        ec2InstanceRepository.saveAll(ec2Instances);
    }

    private void fetchS3Buckets(Job job) {
        List<String> bucketNames = s3Client.listBuckets().buckets().stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
        List<S3Bucket> s3Buckets = new ArrayList<>();
        for (String name : bucketNames) {
            S3Bucket s3Bucket = new S3Bucket();
            s3Bucket.setBucketName(name);
            s3Bucket.setRegion(DEFAULT_REGION);
            s3Bucket.setJob(job);
            s3Buckets.add(s3Bucket);
        }
        s3BucketRepository.saveAll(s3Buckets);
    }
}
