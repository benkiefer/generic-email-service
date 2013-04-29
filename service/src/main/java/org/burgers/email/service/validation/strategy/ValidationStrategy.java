package org.burgers.email.service.validation.strategy;

import org.burgers.email.service.validation.ValidationContext;

public interface ValidationStrategy<T> {
    public void validate(T myObject, ValidationContext validationContext);
}
