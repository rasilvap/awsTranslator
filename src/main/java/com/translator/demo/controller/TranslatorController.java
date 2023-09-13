package com.translator.demo.controller;

import com.translator.demo.service.TranslatorService;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslatorController {

    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/translator")
    private ResponseEntity<TranslatorOutput> getCustomer(@RequestBody TranslatorInput translatorInput) {
        TranslatorOutput translatorOutput = translatorService.translateText(translatorInput);
        return ResponseEntity.ok(translatorOutput);
    }
}
