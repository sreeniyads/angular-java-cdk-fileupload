package com.example.fileupload.fileupload.config;

import com.example.fileupload.fileupload.service.AwsSecretsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Map;

@Configuration
public class S3Config {

    @Autowired
    private AwsSecretsManagerService secretsService;

    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        Map<String, String> secrets = secretsService.getSecret();

        AwsBasicCredentials creds = AwsBasicCredentials.create(
                secrets.get("accessKey"),
                secrets.get("secretKey")
        );

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }
}