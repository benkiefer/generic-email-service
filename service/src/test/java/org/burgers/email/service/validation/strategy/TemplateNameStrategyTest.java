package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.burgers.email.service.validation.rule.TemplateExistsRule;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TemplateNameStrategyTest {
    @InjectMocks
    private TemplateNameStrategy strategy;
    @Mock
    private RequiredFieldRule requiredFieldRule;
    @Mock
    private EmailTemplateRequest emailTemplateRequest;
    @Mock
    private TemplateExistsRule templateExistsRule;
    @Mock
    private ValidationContext context;

    public static final String VALUE = "value";

    @Test
    public void required_value_passes(){
        Mockito.when(emailTemplateRequest.getTemplateName()).thenReturn(VALUE);
        Mockito.when(requiredFieldRule.validate(VALUE, TemplateNameStrategy.FIELD, context)).thenReturn(true);

        strategy.validate(emailTemplateRequest, context);

        Mockito.verify(requiredFieldRule).validate(VALUE, TemplateNameStrategy.FIELD, context);
        Mockito.verify(templateExistsRule).validate(VALUE, TemplateNameStrategy.FIELD, context);
    }

    @Test
    public void required_value_fails(){
        Mockito.when(emailTemplateRequest.getTemplateName()).thenReturn(VALUE);
        Mockito.when(requiredFieldRule.validate(VALUE, TemplateNameStrategy.FIELD, context)).thenReturn(false);

        strategy.validate(emailTemplateRequest, context);

        Mockito.verify(requiredFieldRule).validate(VALUE, TemplateNameStrategy.FIELD, context);
    }

    @After
    public void tearDown(){
        Mockito.verifyNoMoreInteractions(templateExistsRule, requiredFieldRule);
    }

}
