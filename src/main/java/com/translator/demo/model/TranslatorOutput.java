package com.translator.demo.model;

import java.util.Date;

public record TranslatorOutput(String source, String target, String answer, Date timestamp) {}
