package com.translator.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.*;
import com.translator.demo.exception.TranslatorException;
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
    public void translateText_ValidInput_ReturnsTranslatedText() throws TranslatorException {
        TranslatorInput input = new TranslatorInput("es", "en", "Hola");
        String translatedText = "Hola";
        TranslateTextResult result = new TranslateTextResult().withTranslatedText(translatedText);

        when(amazonTranslate.translateText(any(TranslateTextRequest.class))).thenReturn(result);

        TranslatorOutput output = translatorService.translateText(input);

        assertEquals(input.source(), output.source());
        assertEquals(input.target(), output.target());
        assertEquals(translatedText, output.answer());
    }

    @Test
    public void translateText_ValidInput_ReturnsTranslatedText1() throws TranslatorException {
        TranslatorInput input = new TranslatorInput("es", "en", null);

        TranslatorException exception = assertThrows(TranslatorException.class, () -> {
            translatorService.translateText(input);
        });

        assertEquals("source, target and shouldn't be empty or null.", exception.getMessage());
    }

    @Test
    public void translateText_ValidInput_ReturnsTranslatedText2() throws TranslatorException {
        TranslatorInput input = null;

        TranslatorException exception = assertThrows(TranslatorException.class, () -> {
            translatorService.translateText(input);
        });

        assertEquals("translatorInput shouldn't be null.", exception.getMessage());
    }
}