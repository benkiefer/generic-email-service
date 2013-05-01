package org.burgers.email.service.validation;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.ValidationException;
import org.burgers.email.service.validation.strategy.ValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationManager {
    @Autowired
    private List<ValidationStrategy<EmailTemplateRequest>> strategies;
    @Autowired
    private ValidationMessageBuilder validationMessageBuilder;

    public void validate(EmailTemplateRequest request) {
        SimpleValidationContext context = new SimpleValidationContext();

        for (ValidationStrategy<EmailTemplateRequest> strategy : strategies) {
            strategy.validate(request, context);
        }

        if (context.hasErrors()) {
            String message = validationMessageBuilder.build(context);
            throw new ValidationException(message);
        }
    }

    public void setStrategies(List<ValidationStrategy<EmailTemplateRequest>> strategies) {
        this.strategies = strategies;
    }
}
