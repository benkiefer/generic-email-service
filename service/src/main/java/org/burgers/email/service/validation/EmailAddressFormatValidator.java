package org.burgers.email.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.regex.Pattern;

import static java.util.Arrays.asList;

@Component
public class EmailAddressFormatValidator implements RequestValidator {
    public static final String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;

    public EmailAddressFormatValidator() {
        this.pattern = Pattern.compile(PATTERN);
    }

    public void validate(String value, BindingResult bindingResult){
        if (StringUtils.isBlank(value)){
            bindingResult.reject("missing.email.address", "missing email address");
            return;
        }
        if (!pattern.matcher(value).matches()){
            bindingResult.reject("invalid.email.address", asList(value).toArray(), "invalid email address");
        }
    }
}
