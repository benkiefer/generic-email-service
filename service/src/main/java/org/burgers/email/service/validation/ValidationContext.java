package org.burgers.email.service.validation;

import java.util.Set;

public interface ValidationContext {
    public void error(String field, String errorMessage);

    public boolean hasErrors();

    public Set<String> getFieldsInError();

    public Set<String> getErrorsForField(String field);

    public boolean fieldHasErrors(String field);
}
