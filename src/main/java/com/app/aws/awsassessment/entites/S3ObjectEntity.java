package com.app.aws.awsassessment.entites;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "s3_object")
public class S3ObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @NotBlank
    private String bucketName;

    @NotBlank
    private String fileName;
}

