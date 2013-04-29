package org.burgers.email.service.validation.rule;

import org.burgers.email.service.validation.ValidationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailAddressFormatRule {
    public static final String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String MESSAGE = "%s is not a valid email.";

    private Pattern pattern;

    public EmailAddressFormatRule() {
        this.pattern = Pattern.compile(PATTERN);
    }

    public boolean validate(String value, String field, ValidationContext context){
        if (!pattern.matcher(value).matches()){
            context.error(field, String.format(MESSAGE, value));
            return false;
        }
        return true;
    }

}
