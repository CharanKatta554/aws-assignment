package com.app.aws.awsassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.aws.awsassessment.entites.S3ObjectEntity;
import java.util.List;


@Repository
public interface S3ObjectRepository extends JpaRepository<S3ObjectEntity, Long> {

    int countByBucketName(String bucketName);

    List<S3ObjectEntity> findByBucketNameAndFileNameContains(String bucketName, String pattern);
}