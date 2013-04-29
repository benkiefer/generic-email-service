package org.burgers.email.service.validation;


import java.util.*;

public class SimpleValidationContext implements ValidationContext{
    private Map<String, Set<String>> errorMap = new LinkedHashMap<String, Set<String>>();

    @Override
    public void error(String field, String errorMessage) {
        if (!errorMap.containsKey(field)){
            errorMap.put(field, new TreeSet<String>());
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
