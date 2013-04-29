package org.burgers.email.service.validation;

import org.burgers.email.client.TemplateEmailRequest;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToAddressStrategy implements ValidationStrategy<TemplateEmailRequest> {
    public static final String FIELD = "To";
    @Autowired
    private EmailAddressFormatRule emailAddressFormatRule;

    @Autowired
    private RequiredFieldRule requiredFieldRule;

    @Override
    public void validate(TemplateEmailRequest myObject, ValidationContext context) {
        List<String> toAddressBlock = myObject.getTo();

        if (requiredFieldRule.validateAtLeastOne(toAddressBlock, FIELD, context)){
            for (String to : toAddressBlock) {
                if (requiredFieldRule.validate(to, FIELD, context)){
                    emailAddressFormatRule.validate(to, FIELD, context);
                }
            }
        }
    }

    public void setEmailAddressFormatRule(EmailAddressFormatRule emailAddressFormatRule) {
        this.emailAddressFormatRule = emailAddressFormatRule;
    }

    public void setRequiredFieldRule(RequiredFieldRule requiredFieldRule) {
        this.requiredFieldRule = requiredFieldRule;
    }
}
