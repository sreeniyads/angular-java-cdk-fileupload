package com.example.fileupload.fileupload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {
    private String region;
    private S3 s3 = new S3();
    private Credentials credentials = new Credentials();

    public static class S3 {
        private String bucket;
        public String getBucket() { return bucket; }
        public void setBucket(String bucket) { this.bucket = bucket; }
    }

    public static class Credentials {
        private String accessKey;
        private String secretKey;
        public String getAccessKey() { return accessKey; }
        public void setAccessKey(String accessKey) { this.accessKey = accessKey; }
        public String getSecretKey() { return secretKey; }
        public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public S3 getS3() { return s3; }
    public Credentials getCredentials() { return credentials; }
}
