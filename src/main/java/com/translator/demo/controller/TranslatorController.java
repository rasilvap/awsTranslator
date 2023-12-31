package com.translator.demo.controller;

import com.translator.demo.exception.TranslatorException;
import com.translator.demo.service.TranslatorService;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TranslatorController {

    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/translator")
    private ResponseEntity<TranslatorOutput> translateText(@RequestBody TranslatorInput translatorInput) {
        TranslatorOutput translatorOutput;
        try {
            translatorOutput = translatorService.translateText(translatorInput);
            return ResponseEntity.ok(translatorOutput);
        } catch (TranslatorException e) {
            String errorMessage = "There is an error in the translation: " + e.getMessage();
            TranslatorOutput errorOutput = new TranslatorOutput("", "", errorMessage, LocalDateTime.now());
            return ResponseEntity.badRequest().body(errorOutput);
        }
    }
}
