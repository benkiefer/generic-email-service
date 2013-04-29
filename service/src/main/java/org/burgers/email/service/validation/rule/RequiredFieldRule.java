package org.burgers.email.service.validation.rule;

import org.apache.commons.lang3.StringUtils;
import org.burgers.email.service.validation.ValidationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RequiredFieldRule {
    public static final String MESSAGE = "Missing Required Field";

    public boolean validateAtLeastOne(Collection value, String field, ValidationContext context){
        if (value == null || value.isEmpty()){
            context.error(field, MESSAGE);
            return false;
        }
        return true;
    }

    public boolean validate(String value, String field, ValidationContext context){
        if (StringUtils.isBlank(value)){
            context.error(field, MESSAGE);
            return false;
        }
        return true;
    }
}
