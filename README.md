# AWS Assignment Task

This repository contains a Spring Boot application that performs various AWS operations asynchronously, including fetching all instance IDs, S3 buckets, and files within S3 buckets in a given region.
## Features

- Discover EC2, S3 AWS servies
    ```bash
    curl --location 'http://localhost:8080/aws/discover' \
    --header 'Content-Type: application/json' \
    --data '["S3","EC2"]'
    ```
    
- Get job status by job id.
    ```bash
     curl --location 'http://localhost:8080/aws/job/{jobId}'
    ```
- Discover all the objects inside the AWS services like instancesIds in EC2, objects inside S3.

  For S3:
   ```bash
      curl --location 'http://localhost:8080/aws/discover?serviceName=S3'
    ```
  response:
    ```json
      [
            "bucket_1",
            "bucket_2"
      ]
    ```
  For EC2:
  
    ```bash
      curl --location 'http://localhost:8080/aws/discover?serviceName=EC2'
    ```
    response:
    ```json
      [
            "instance_id_1",
            "instance_id_2"
      ]
    ```
- Fetch all the objects in a given s3 bucket name.
    ```bash
      curl --location --request POST 'http://localhost:8080/aws/s3/{s3_bucket_name}'
    ```
- Get number of objects inside given bucket.
    ```bash
      curl --location 'http://localhost:8080/aws/s3/{s3_bucket_name}/count'
    ```
- Get all s3 objects in a given bucket on matching their file names with given file pattern.
    ```bash
    curl --location 'http://localhost:8080/aws/s3/{s3_bucket_name}?filePattern=File'
    ```

## Getting Started

### Prerequisites

- Java 22 or higher
- Maven
- AWS SDK v2
- Spring Boot
- MySQL database.

### Setup

Clone the repository:
   ```bash
   git clone git@github.com:CharanKatta554/aws-assignment.git
   cd aws-assignment
  ```
Install maven dependcies:
  ```bash
    mvn clean install
  ```
Run the application:
  ```bash
    mvn spring-boot:run
  ```
