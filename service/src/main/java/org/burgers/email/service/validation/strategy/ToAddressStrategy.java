package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToAddressStrategy implements ValidationStrategy<EmailTemplateRequest> {
    public static final String FIELD = "To";
    @Autowired
    private EmailAddressFormatRule emailAddressFormatRule;

    @Autowired
    private RequiredFieldRule requiredFieldRule;

    @Override
    public void validate(EmailTemplateRequest myObject, ValidationContext context) {
        List<String> toAddressBlock = myObject.getTo();

        if (requiredFieldRule.validateAtLeastOne(toAddressBlock, FIELD, context)){
            for (String to : toAddressBlock) {
                if (requiredFieldRule.validate(to, FIELD, context)){
                    emailAddressFormatRule.validate(to, FIELD, context);
                }
            }
        }
    }

}
