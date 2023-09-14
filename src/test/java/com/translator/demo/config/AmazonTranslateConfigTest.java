package com.translator.demo.config;

import com.amazonaws.services.translate.AmazonTranslate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AmazonTranslateConfigTest {
    @Autowired
    private AmazonTranslate amazonTranslate;

    @Test
    public void testAmazonTranslateBeanCreation() {
        assertNotNull(amazonTranslate);
    }
}