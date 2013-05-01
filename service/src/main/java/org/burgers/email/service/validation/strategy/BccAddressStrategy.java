package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BccAddressStrategy extends OptionalCopiedAddressStrategy {
    @Override
    protected List<String> getOptionalAddresses(EmailTemplateRequest templateRequest) {
        return templateRequest.getBcc();
    }

    @Override
    protected String getField() {
        return "BCC";
    }
}
