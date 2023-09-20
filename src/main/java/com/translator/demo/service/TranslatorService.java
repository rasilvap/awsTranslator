package com.translator.demo.service;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.translator.demo.exception.TranslatorException;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TranslatorService {
    private final AmazonTranslate amazonTranslate;
    public TranslatorService(AmazonTranslate amazonTranslate) {
        this.amazonTranslate = amazonTranslate;
    }

    public TranslatorOutput translateText(TranslatorInput translatorInput) throws TranslatorException {

        if (translatorInput == null) {
            throw new TranslatorException("translatorInput shouldn't be null.");
        }

        Optional<String> source = Optional.ofNullable(translatorInput.source());
        Optional <String> target = Optional.ofNullable(translatorInput.target());
        Optional<String> text =  Optional.ofNullable(translatorInput.text());

        if (isEmpty(source) || isEmpty(target) || isEmpty(text)) {
            throw new TranslatorException("source, target and shouldn't be empty or null.");
        }

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(text.get())
                .withSourceLanguageCode(source.get())
                .withTargetLanguageCode(target.get());
        TranslateTextResult result = amazonTranslate.translateText(request);
        TranslatorOutput translatorOutput = new TranslatorOutput(translatorInput.source(),
                translatorInput.target(), result.getTranslatedText(), LocalDateTime.now());
        return translatorOutput;
    }

    private boolean isEmpty(Optional<String> str) {
        return str.map(s -> s.trim().isEmpty()).orElse(true);
    }
}
