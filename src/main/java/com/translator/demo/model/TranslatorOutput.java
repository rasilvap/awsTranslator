package com.translator.demo.model;

public record TranslatorOutput(String source, String target, String answer, java.time.LocalDateTime timestamp) {}
