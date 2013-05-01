package org.burgers.email.service.validation.rule;

import freemarker.template.Configuration;
import org.burgers.email.service.validation.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TemplateExistsRule {
    public static final String MESSAGE = "%s is not a supported template.";

    @Autowired
    private Configuration configuration;

    public boolean validate(String value, String field, ValidationContext context) {
        try {
            configuration.getTemplate(value);
        } catch (IOException e) {
            context.error(field, String.format(MESSAGE, value));
            return false;
        }
        return true;
    }
}
