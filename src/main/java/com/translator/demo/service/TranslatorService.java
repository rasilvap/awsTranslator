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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TranslatorService {
    private final AmazonTranslate amazonTranslate;
    public TranslatorService(AmazonTranslate amazonTranslate) {
        this.amazonTranslate = amazonTranslate;
    }

    public TranslatorOutput translateText(TranslatorInput translatorInput) {
        TranslateTextRequest request = new TranslateTextRequest()
                .withText(translatorInput.text())
                .withSourceLanguageCode(translatorInput.source())
                .withTargetLanguageCode(translatorInput.target());
        TranslateTextResult result = amazonTranslate.translateText(request);
        TranslatorOutput translatorOutput = new TranslatorOutput(translatorInput.source(),
                translatorInput.target(), result.getTranslatedText(), LocalDateTime.now());
        return translatorOutput;
    }
}
