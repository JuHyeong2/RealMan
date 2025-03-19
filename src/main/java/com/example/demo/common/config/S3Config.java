package com.example.demo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Bean
	public AmazonS3Client amazonS3Client() {
		// AWS 인증 정보 (Access Key, Secret Key) 설정
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		return (AmazonS3Client) AmazonS3ClientBuilder
													.standard() // 기본설정
													.withRegion(region)
													.withCredentials(new AWSStaticCredentialsProvider(credentials))
													.build();
	}
}