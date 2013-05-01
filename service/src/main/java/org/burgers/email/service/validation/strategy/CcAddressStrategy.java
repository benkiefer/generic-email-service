package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CcAddressStrategy extends OptionalCopiedAddressStrategy {
    @Override
    protected List<String> getOptionalAddresses(EmailTemplateRequest templateRequest) {
        return templateRequest.getCc();
    }

    @Override
    protected String getField() {
        return "CC";
    }
}
