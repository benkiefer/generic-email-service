package org.burgers.email.service.validation;

import org.burgers.email.client.TemplateEmailRequest;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FromAddressStrategy implements ValidationStrategy<TemplateEmailRequest> {
    public static final String FIELD = "From";

    @Autowired
    private RequiredFieldRule requiredFieldRule;

    @Autowired
    private EmailAddressFormatRule emailAddressFormatRule;

    public void validate(TemplateEmailRequest myObject, ValidationContext validationContext) {
        String from = myObject.getFrom();

        if (requiredFieldRule.validate(from, FIELD, validationContext)){
            emailAddressFormatRule.validate(from, FIELD, validationContext);
        }
    }
}
