package com.translator.demo.controller;

import com.amazonaws.services.translate.AmazonTranslate;
import com.translator.demo.service.TranslatorService;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import com.translator.demo.util.AwsTranslateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslatorController {

    private final TranslatorService translatorService;
    private  AmazonTranslate amazonTranslate;

    private static final String REGION = "us-east-1";

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/translator")
    private ResponseEntity<TranslatorOutput> translateText(@RequestBody TranslatorInput translatorInput) {
        amazonTranslate = AwsTranslateUtil.getAmazonTranslateInstance();
        translatorService.setAmazonTranslate(amazonTranslate);
        TranslatorOutput translatorOutput = translatorService.translateText(translatorInput);
        return ResponseEntity.ok(translatorOutput);
    }
}
