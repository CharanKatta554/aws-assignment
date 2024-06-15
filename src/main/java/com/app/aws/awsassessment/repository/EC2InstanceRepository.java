package com.app.aws.awsassessment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app.aws.awsassessment.entites.EC2Instance;

@Repository
public interface EC2InstanceRepository extends JpaRepository<EC2Instance, Long> {
    @Query("SELECT e.instanceId FROM EC2Instance e")
    List<String> findAllNames();
}