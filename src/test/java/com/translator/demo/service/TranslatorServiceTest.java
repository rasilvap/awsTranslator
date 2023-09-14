package com.translator.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.*;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranslatorServiceTest {

    private TranslatorService translatorService;
    private AmazonTranslate amazonTranslate;

    @BeforeEach
    public void setUp() {
        amazonTranslate = mock(AmazonTranslate.class);
        translatorService = new TranslatorService(amazonTranslate);
    }
    @Test
    public void translateText_ValidInput_ReturnsTranslatedText() {
        // Arrange
        TranslatorInput input = new TranslatorInput("Hello", "en", "es");
        String translatedText = "Hola";
        TranslateTextResult result = new TranslateTextResult().withTranslatedText(translatedText);

        when(amazonTranslate.translateText(any(TranslateTextRequest.class))).thenReturn(result);

        // Act
        TranslatorOutput output = translatorService.translateText(input);

        // Assert
        assertEquals(input.source(), output.source());
        assertEquals(input.target(), output.target());
        assertEquals(translatedText, output.answer());
    }
}