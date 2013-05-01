package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FromAddressStrategy implements ValidationStrategy<EmailTemplateRequest> {
    public static final String FIELD = "From";

    @Autowired
    private RequiredFieldRule requiredFieldRule;

    @Autowired
    private EmailAddressFormatRule emailAddressFormatRule;

    public void validate(EmailTemplateRequest myObject, ValidationContext validationContext) {
        String from = myObject.getFrom();

        if (requiredFieldRule.validate(from, FIELD, validationContext)){
            emailAddressFormatRule.validate(from, FIELD, validationContext);
        }
    }
}
