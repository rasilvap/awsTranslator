package com.translator.demo.controller;

import com.translator.demo.exception.TranslatorException;
import com.translator.demo.model.TranslatorInput;
import com.translator.demo.model.TranslatorOutput;
import com.translator.demo.service.TranslatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TranslatorController.class)
class TranslatorControllerTest {

    private MockMvc mockMvc;
    @MockBean
    TranslatorService translatorService;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testTranslatorEndpointSuccess() throws Exception {
        TranslatorOutput expectedOutput =  new TranslatorOutput("en", "es", "Hola mundo", LocalDateTime.of(2023, 1, 1, 10, 30));
        when(translatorService.translateText(any(TranslatorInput.class))).thenReturn(expectedOutput);

        mockMvc.perform(get("/translator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"source\": \"en\",\n" +
                                "\"target\": \"es\",\n" +
                                "\"text\": \"Hello world\"\n" +
                                "}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(("{\n" +
                        "\"source\": \"en\",\n" +
                        "\"target\": \"es\",\n" +
                        "\"answer\": \"Hola mundo\",\n" +
                        "\"timestamp\": \"2023-01-01T10:30:00\"\n" +
                        "}")));
    }

    @Test
    public void testTranslatorEndpointBadRequestWhenTextIsNull() throws Exception {
        String expectedErrorMessage = "There is an error in the translation: source, target, and text shouldn't be empty or null.";


        when(translatorService.translateText(any(TranslatorInput.class))).thenThrow(new TranslatorException("source, target, and text shouldn't be empty or null."));

        MvcResult result = mockMvc.perform(get("/translator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"target\": \"es\",\n" +
                                "\"text\": \"Hello world\"\n" +
                                "}")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        // Comparar el mensaje de error esperado con la respuesta
        assertTrue(responseContent.contains(expectedErrorMessage));
    }

}