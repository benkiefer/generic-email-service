package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateNameStrategy implements ValidationStrategy<EmailTemplateRequest> {
    public static final String FIELD = "Template Name";

    @Autowired
    private RequiredFieldRule requiredFieldRule;

    @Override
    public void validate(EmailTemplateRequest myObject, ValidationContext validationContext) {
        requiredFieldRule.validate(myObject.getTemplateName(), FIELD, validationContext);
    }
}
