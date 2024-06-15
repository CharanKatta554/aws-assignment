package com.app.aws.awsassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app.aws.awsassessment.entites.Job;
import com.app.aws.awsassessment.enums.JobStatus;
import jakarta.transaction.Transactional;


@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.status = :status WHERE j.id = :id")
    int updateJobStatusById(Long id, JobStatus status);

    Job findOneById(Long id);
}