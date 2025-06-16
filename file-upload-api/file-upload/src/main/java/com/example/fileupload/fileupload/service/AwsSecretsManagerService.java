package com.example.fileupload.fileupload.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.io.IOException;
import java.util.Map;

@Service
public class AwsSecretsManagerService {
    @Value("${aws.region}")
    private String region;

    public Map<String, String> getSecret() {

            String secretName = "app/credentials";

            SecretsManagerClient client = SecretsManagerClient.builder()
                    .region(Region.of(region))
                    .build();

            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            try {
                GetSecretValueResponse response = client.getSecretValue(getSecretValueRequest);
                String secretJson = response.secretString();
                System.out.println("secrteJson: " + secretJson);
                return new ObjectMapper().readValue(secretJson, new TypeReference<>() {});
            } catch (Exception e) {
                throw new RuntimeException("Unable to retrieve or parse secret", e);
            }
        }
}
