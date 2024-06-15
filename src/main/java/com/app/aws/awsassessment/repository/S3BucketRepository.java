package com.app.aws.awsassessment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app.aws.awsassessment.entites.S3Bucket;

@Repository
public interface S3BucketRepository extends JpaRepository<S3Bucket, Long> {
    @Query("SELECT s.bucketName FROM S3Bucket s")
    List<String> findAllNames();
}
