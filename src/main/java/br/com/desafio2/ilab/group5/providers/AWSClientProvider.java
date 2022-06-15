package br.com.desafio2.ilab.group5.providers;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

public class AWSClientProvider {
	public static SqsClient clientAuth(){
		AwsCredentialsProvider credentialsProvider = new AwsCredentialsProvider() {
	        @Override
	        public AwsCredentials resolveCredentials() {
	            return new AwsCredentials() {
	                @Override
	                public String accessKeyId() {
	                    return System.getenv("AWS_ACCESS_KEY");
	                }

	                @Override
	                public String secretAccessKey() {
	                    return System.getenv("AWS_SECRET_KEY");
	                }
	            };
	        }
	    };
	    SqsClient sqsClient = SqsClient.builder()
	            .region(Region.US_EAST_1)
	            .credentialsProvider(credentialsProvider)
	            .build();

	    return sqsClient;
	}
}
