package org.burgers.email.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmailAddressFormatValidatorTest {
    private EmailAddressFormatValidator validator;
    private BindingResult bindingResult;

    @Before
    public void setup(){
        validator = new EmailAddressFormatValidator();
        bindingResult = new BeanPropertyBindingResult("x", "x");
    }

    @Test
    public void validate_no_domain(){
        validator.validate("test@", bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void validate_valid(){
        validator.validate("test@test.com", bindingResult);

        assertFalse(bindingResult.hasErrors());
    }

    @Test
    public void validate_valid_name_characters(){
        validator.validate("test.test@test.com", bindingResult);

        assertFalse(bindingResult.hasErrors());
    }

    @Test
    public void validate_noaddress(){
        validator.validate("@test.com", bindingResult);

        assertTrue(bindingResult.hasErrors());
    }

}
