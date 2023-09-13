package com.translator.demo.util;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;

public class AwsTranslateUtil {
    private static final String REGION = "us-east-1";

    public static AmazonTranslate getAmazonTranslateInstance(){
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        AmazonTranslate amazonTranslate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds.getCredentials()))
                .withRegion(REGION)
                .build();
        return amazonTranslate;
    }
}
