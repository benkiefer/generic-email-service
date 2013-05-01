package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class OptionalCopiedAddressStrategy implements ValidationStrategy<EmailTemplateRequest> {
    @Autowired
    private EmailAddressFormatRule emailAddressFormatRule;

    public void validate(EmailTemplateRequest myObject, ValidationContext validationContext) {
        for (String address : getOptionalAddresses(myObject)) {
            emailAddressFormatRule.validate(address, getField(), validationContext);
        }
    }

    protected abstract List<String> getOptionalAddresses(EmailTemplateRequest templateRequest);

    protected abstract String getField();

    public void setEmailAddressFormatRule(EmailAddressFormatRule emailAddressFormatRule) {
        this.emailAddressFormatRule = emailAddressFormatRule;
    }
}
