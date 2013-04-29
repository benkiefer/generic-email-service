package org.burgers.email.service.validation;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleValidationContext implements ValidationContext{
    private Map<String, Set<String>> errorMap = new HashMap<String, Set<String>>();

    @Override
    public void error(String field, String errorMessage) {
        if (!errorMap.containsKey(field)){
            errorMap.put(field, new HashSet<String>());
        }
        errorMap.get(field).add(errorMessage);
    }

    @Override
    public boolean hasErrors() {
        return !errorMap.isEmpty();
    }

    @Override
    public Set<String> getFieldsInError() {
        return errorMap.keySet();
    }

    @Override
    public Set<String> getErrorsForField(String field) {
        return errorMap.get(field);
    }

    @Override
    public boolean fieldHasErrors(String field) {
        return errorMap.containsKey(field);
    }
}
