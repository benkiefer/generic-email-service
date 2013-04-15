package org.burgers.email.service;

public class TemplateProcessingException extends RuntimeException {
    public TemplateProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
