package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.TemplateEmailRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.burgers.email.service.validation.rule.RequiredFieldRule;
import org.burgers.email.service.validation.strategy.ToAddressStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class ToAddressStrategyTest {
    @InjectMocks
    private ToAddressStrategy strategy;
    @Mock
    private EmailAddressFormatRule emailAddressFormatRule;
    @Mock
    private RequiredFieldRule requiredFieldRule;
    @Mock
    private ValidationContext context;
    @Mock
    private TemplateEmailRequest request;

    @Test
    public void empty_list(){
        List<String> toAddressBlock = new ArrayList<String>();
        Mockito.when(request.getTo()).thenReturn(toAddressBlock);
        Mockito.when(requiredFieldRule.validateAtLeastOne(toAddressBlock, ToAddressStrategy.FIELD, context)).thenReturn(false);
        strategy.validate(request, context);

        Mockito.verify(requiredFieldRule).validateAtLeastOne(toAddressBlock, ToAddressStrategy.FIELD, context);
        Mockito.verifyNoMoreInteractions(context, requiredFieldRule, emailAddressFormatRule);
    }

    @Test
    public void not_empty_list_invalid_value(){
        String value = "hi";
        List<String> toAddressBlock = asList(value);

        Mockito.when(request.getTo()).thenReturn(toAddressBlock);
        Mockito.when(requiredFieldRule.validateAtLeastOne(toAddressBlock, ToAddressStrategy.FIELD, context)).thenReturn(true);
        Mockito.when(requiredFieldRule.validate(value, ToAddressStrategy.FIELD, context)).thenReturn(false);

        strategy.validate(request, context);

        Mockito.verify(requiredFieldRule).validateAtLeastOne(toAddressBlock, ToAddressStrategy.FIELD, context);
        Mockito.verify(requiredFieldRule).validate(value, ToAddressStrategy.FIELD, context);

        Mockito.verifyNoMoreInteractions(requiredFieldRule, emailAddressFormatRule);
    }

    @Test
    public void not_empty_list_invalid_email(){
        String value = "hi";
        List<String> toAddressBlock = asList(value);

        Mockito.when(request.getTo()).thenReturn(toAddressBlock);
        Mockito.when(requiredFieldRule.validateAtLeastOne(toAddressBlock, ToAddressStrategy.FIELD, context)).thenReturn(true);
        Mockito.when(requiredFieldRule.validate(value, ToAddressStrategy.FIELD, context)).thenReturn(true);

        strategy.validate(request, context);

        Mockito.verify(requiredFieldRule).validateAtLeastOne(toAddressBlock, ToAddressStrategy.FIELD, context);
        Mockito.verify(requiredFieldRule).validate(value, ToAddressStrategy.FIELD, context);
        Mockito.verify(emailAddressFormatRule).validate(value, ToAddressStrategy.FIELD, context);

        Mockito.verifyNoMoreInteractions(requiredFieldRule, emailAddressFormatRule);
    }


}
