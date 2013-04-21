package org.burgers.email.service.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EmailAddressFormatValidatorTest {
    @InjectMocks
    private EmailAddressFormatValidator validator;
    @Mock
    private BindingResult bindingResult;
    @Captor
    private ArgumentCaptor<Object[]> argumentCaptor;
    private String value;

    @Test
    public void validate_no_domain(){
        value = "test@";
        validator.validate(value, bindingResult);
        verifyInvalidEmail();
    }

    @Test
    public void validate_valid(){
        value = "test@test.com";

        validator.validate(value, bindingResult);

        verifyNoMoreInteractions(bindingResult);
    }

    @Test
    public void validate_valid_name_characters(){
        value = "test.test@test.com";
        validator.validate(value, bindingResult);

        verifyNoMoreInteractions(bindingResult);
    }

    @Test
    public void validate_noaddress(){
        value = "@test.com";
        validator.validate(value, bindingResult);

        verifyInvalidEmail();
    }

    @Test
    public void validate_missing(){
        validator.validate("", bindingResult);

        verifyMissingEmail();
    }

    private void verifyInvalidEmail() {
        verify(bindingResult).reject(eq("invalid.email.address"), argumentCaptor.capture(), eq("invalid email address"));
        List<Object> args = asList(argumentCaptor.getValue());
        assertEquals(1, args.size());
        String emailAddress = (String) args.get(0);
        assertEquals(emailAddress, value);
        verifyNoMoreInteractions(bindingResult);
    }

    private void verifyMissingEmail() {
        verify(bindingResult).reject("missing.email.address", "missing email address");
        verifyNoMoreInteractions(bindingResult);
    }


}
