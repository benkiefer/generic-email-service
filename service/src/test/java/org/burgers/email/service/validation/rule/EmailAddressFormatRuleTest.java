package org.burgers.email.service.validation.rule;

import org.burgers.email.service.validation.ValidationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EmailAddressFormatRuleTest {
    public static final String FIELD = "field";
    @InjectMocks
    private EmailAddressFormatRule validator;
    @Mock
    private ValidationContext context;
    private String value;

    @Test
    public void validate_no_domain() {
        value = "test@";
        assertFalse(validator.validate(value, FIELD, context));
        verify(context).error(FIELD, String.format(EmailAddressFormatRule.MESSAGE, value));
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_valid() {
        value = "test@test.com";

        assertTrue(validator.validate(value, FIELD, context));
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_valid_name_characters() {
        value = "test.test@test.com";

        assertTrue(validator.validate(value, FIELD, context));
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_noaddress() {
        value = "@test.com";

        assertFalse(validator.validate(value, FIELD, context));
        verify(context).error(FIELD, String.format(EmailAddressFormatRule.MESSAGE, value));
        verifyNoMoreInteractions(context);
    }

}
