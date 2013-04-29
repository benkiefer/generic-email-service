package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.TemplateEmailRequest;
import org.burgers.email.service.ValidationException;
import org.burgers.email.service.validation.SimpleValidationContext;
import org.burgers.email.service.validation.ValidationManager;
import org.burgers.email.service.validation.ValidationMessageBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidationManager.class)
public class ValidationManagerTest {
    public static final String MESSAGE = "message";
    @InjectMocks
    private ValidationManager manager;
    @Mock
    private ValidationMessageBuilder messageBuilder;
    @Mock
    private SimpleValidationContext context;
    @Mock
    private ToAddressStrategy toAddressStrategy;
    @Mock
    private FromAddressStrategy fromAddressStrategy;
    @Mock
    private TemplateNameStrategy templateNameStrategy;

    private TemplateEmailRequest request;

    @Before
    public void setup() throws Exception {
        PowerMockito.whenNew(SimpleValidationContext.class).withNoArguments().thenReturn(context);
    }

    @Test
    public void validate(){
        Mockito.when(context.hasErrors()).thenReturn(false);
        manager.validate(request);
        Mockito.verify(toAddressStrategy).validate(request, context);
        Mockito.verify(fromAddressStrategy).validate(request, context);
        Mockito.verify(templateNameStrategy).validate(request, context);
        Mockito.verifyNoMoreInteractions(toAddressStrategy, messageBuilder);
    }

    @Test
    public void validate_fail(){
        Mockito.when(context.hasErrors()).thenReturn(true);
        Mockito.when(messageBuilder.build(context)).thenReturn(MESSAGE);
        try {
            manager.validate(request);
            fail("expected failure");
        } catch (ValidationException e){
            assertEquals(MESSAGE, e.getMessage());
        }

        Mockito.verify(toAddressStrategy).validate(request, context);
        Mockito.verify(fromAddressStrategy).validate(request, context);
        Mockito.verify(templateNameStrategy).validate(request, context);
        Mockito.verify(messageBuilder).build(context);
        Mockito.verifyNoMoreInteractions(toAddressStrategy, messageBuilder);
    }

}
