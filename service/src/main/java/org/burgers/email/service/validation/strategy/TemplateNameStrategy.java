package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.burgers.email.service.validation.rule.TemplateExistsRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateNameStrategy implements ValidationStrategy<EmailTemplateRequest> {
    public static final String FIELD = "Template Name";

    @Autowired
    private RequiredFieldRule requiredFieldRule;

    @Autowired
    private TemplateExistsRule templateExistsRule;

    @Override
    public void validate(EmailTemplateRequest myObject, ValidationContext validationContext) {
        String templateName = myObject.getTemplateName();
        if (requiredFieldRule.validate(templateName, FIELD, validationContext)){
            templateExistsRule.validate(templateName, FIELD, validationContext);
        }
    }
}
