package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.TemplateEmailRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
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
    private TemplateEmailRequest templateEmailRequest;
    @Mock
    private ValidationContext context;

    public static final String VALUE = "value";

    @Test
    public void valid(){
        Mockito.when(templateEmailRequest.getTemplateName()).thenReturn(VALUE);
        strategy.validate(templateEmailRequest, context);
        Mockito.verify(requiredFieldRule).validate(VALUE, TemplateNameStrategy.FIELD, context);
    }

}
