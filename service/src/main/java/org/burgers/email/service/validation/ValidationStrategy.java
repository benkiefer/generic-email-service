package org.burgers.email.service.validation;

public interface ValidationStrategy<T> {
    public void validate(T myObject, ValidationContext validationContext);
}
