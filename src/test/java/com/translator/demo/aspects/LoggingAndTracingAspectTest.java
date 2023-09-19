package com.translator.demo.aspects;

import com.translator.demo.exception.TranslatorException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
@SpringBootTest
class LoggingAndTracingAspectTest {
    @Mock
    private JoinPoint joinPoint; // Used to represent a specific execution point in the program for Aspect-Oriented Programming (AOP) operations.
    @Mock
    private Signature signature; // Represents the signature (method details) of the current execution point for AOP operations.

    @InjectMocks
    private LoggingAndTracingAspect loggingAndTracingAspect; // Represents the AOP aspect responsible for logging and tracing method executions.

    @Mock
    private Logger logger;  // Provides logging capabilities for recording information during program execution.


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(joinPoint.getSignature()).thenReturn(signature);

        setPrivateField(loggingAndTracingAspect, "logger", logger);
    }

    @Test
    public void testBeforeServiceMethod() {
        // Arrange
        String methodName = "translateText";
        configureSignature(methodName);

        // Act
        // Trigger the 'beforeServiceMethod' aspect to log the start of a service method execution.
        loggingAndTracingAspect.beforeServiceMethod(joinPoint);

        // Assert
        verify(logger).info("Starting method: " + methodName);
    }

    @Test
    public void testAfterReturningServiceMethod() {
        // Arrange
        String methodName = "translateText";
        configureSignature(methodName);
        Object result = new Object(); // Puedes reemplazar esto con el resultado real

        // Act
        // Trigger the 'afterReturningServiceMethod' aspect to log the end of a service method execution.
        loggingAndTracingAspect.afterReturningServiceMethod(joinPoint, result);

        // Assert
        verify(logger).info("Ending method: " + methodName);
    }

    @Test
    public void testAfterThrowingServiceMethod() {
        // Arrange
        String methodName = "translateText";
        configureSignature(methodName);
        Throwable exception = new TranslatorException("Test Exception");

        // Act
        // Trigger the 'afterThrowingServiceMethod' aspect to log exceptions that occur during service method execution.
        loggingAndTracingAspect.afterThrowingServiceMethod(joinPoint, exception);

        // Assert
        verify(logger).error("Exception in method " + methodName + ": " + exception.getMessage());
    }

    /**
     * Configures the behavior of a mock Signature object to return the specified
     * method name when its getName() method is called. This is useful for preparing
     * the mock object to mimic a specific method invocation signature.
     *
     * @param methodName The name of the method to be returned by Signature's getName().
     */
    private void configureSignature(String methodName) {
        when(signature.getName()).thenReturn(methodName);
    }

    /**
     * Modifies a private field of an object using reflection. This method is useful
     * for testing scenarios where direct access to a private field is required,
     * typically when there are no public methods available to set the field's value.
     *
     * @param object    The instance of the object containing the private field.
     * @param fieldName The name of the private field to modify.
     * @param value     The new value to set for the private field.
     */
    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}