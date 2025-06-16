package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.secretsmanager.Secret;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.secretsmanager.SecretStringGenerator;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class FileUploadInfraStack extends Stack {
    public FileUploadInfraStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public FileUploadInfraStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Bucket bucket = Bucket.Builder.create(this, "FileUploadBucket")
                .versioned(true)
                .removalPolicy(RemovalPolicy.DESTROY)
                .autoDeleteObjects(true)
                .build();

        Secret secret = Secret.Builder.create(this, "AppSecret")
                .secretName("app/credentials")
                .generateSecretString(SecretStringGenerator.builder()
                        .secretStringTemplate("{\"accessKey\":\"dummyKey\", \"secretKey\":\"dummySecret\"}")
                        .generateStringKey("extra")
                        .build())
                .build();

        CfnOutput.Builder.create(this, "BucketName")
                .value(bucket.getBucketName())
                .build();
    }
}
