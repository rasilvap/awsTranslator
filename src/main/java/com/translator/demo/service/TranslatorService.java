package com.translator.demo.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TranslatorService {
    private static final String REGION = "us-east-1";

    public TranslatorOutput translateText(TranslatorInput translatorInput) {
        // Create credentials using a provider chain. For more information, see
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds.getCredentials()))
                .withRegion(REGION)
                .build();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(translatorInput.text())
                .withSourceLanguageCode(translatorInput.source())
                .withTargetLanguageCode(translatorInput.target());
        TranslateTextResult result = translate.translateText(request);
        TranslatorOutput translatorOutput = new TranslatorOutput(translatorInput.source(),
                translatorInput.target(), result.getTranslatedText(), new Date());
        return translatorOutput;
    }
}
