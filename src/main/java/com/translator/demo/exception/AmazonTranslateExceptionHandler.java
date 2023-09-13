package com.translator.demo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.amazonaws.services.translate.model.AmazonTranslateException;
@ControllerAdvice
public class AmazonTranslateExceptionHandler {

    @ExceptionHandler(AmazonTranslateException.class)
    public ResponseEntity<String> handleAmazonTranslateException(AmazonTranslateException ex) {
        String errorMessage = "El token de seguridad incluido en la solicitud ha caducado.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
