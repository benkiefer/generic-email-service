package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FromAddressStrategyTest {
    public static final String FROM = "from";
    @InjectMocks
    private FromAddressStrategy strategy;
    @Mock
    private ValidationContext context;
    @Mock
    private RequiredFieldRule requiredFieldRule;
    @Mock
    private EmailAddressFormatRule emailAddressFormatRule;
    @Mock
    private EmailTemplateRequest emailRequest;

    @Before
    public void setup() {
        Mockito.when(emailRequest.getFrom()).thenReturn(FROM);
    }

    @Test
    public void valid() {
        Mockito.when(requiredFieldRule.validate(FROM, FromAddressStrategy.FIELD, context)).thenReturn(true);
        strategy.validate(emailRequest, context);
        Mockito.verify(requiredFieldRule).validate(FROM, FromAddressStrategy.FIELD, context);
        Mockito.verify(emailAddressFormatRule).validate(FROM, FromAddressStrategy.FIELD, context);
        Mockito.verifyNoMoreInteractions(requiredFieldRule, emailAddressFormatRule);
    }

    @Test
    public void missing_field() {
        Mockito.when(requiredFieldRule.validate(FROM, FromAddressStrategy.FIELD, context)).thenReturn(false);
        strategy.validate(emailRequest, context);
        Mockito.verify(requiredFieldRule).validate(FROM, FromAddressStrategy.FIELD, context);
        Mockito.verifyNoMoreInteractions(requiredFieldRule, emailAddressFormatRule);
    }


}
