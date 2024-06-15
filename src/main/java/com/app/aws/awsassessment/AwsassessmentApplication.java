package com.app.aws.awsassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.app.aws.awsassessment.entites")
@ComponentScan("com.app.aws.awsassessment")
@EnableJpaRepositories("com.app.aws.awsassessment.repository")
public class AwsassessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsassessmentApplication.class, args);
	}

}
