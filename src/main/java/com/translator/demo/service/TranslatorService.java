package com.translator.demo.service;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TranslatorService {

    private  AmazonTranslate amazonTranslate;
    public void setAmazonTranslate(AmazonTranslate translate) {
        this.amazonTranslate = translate;
    }

    public TranslatorOutput translateText(TranslatorInput translatorInput) {
        // Create credentials using a provider chain. For more information, see
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(translatorInput.text())
                .withSourceLanguageCode(translatorInput.source())
                .withTargetLanguageCode(translatorInput.target());
        TranslateTextResult result = amazonTranslate.translateText(request);
        TranslatorOutput translatorOutput = new TranslatorOutput(translatorInput.source(),
                translatorInput.target(), result.getTranslatedText(), new Date());
        return translatorOutput;
    }
}
