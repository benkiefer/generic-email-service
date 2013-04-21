package org.burgers.email.service.validation;

import org.springframework.validation.BindingResult;

public interface RequestValidator {
    public void validate(String value, BindingResult bindingResult);
}
