package com.app.aws.awsassessment.enums;

public enum AWSServiceName {
    S3("s3"),
    EC2("ec2");

    String value;

    AWSServiceName(String value) {
        this.value = value;      
    }

}
