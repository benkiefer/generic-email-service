package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
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

import static java.util.Arrays.asList;
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
    private ValidationStrategy<EmailTemplateRequest> strategy;

    private EmailTemplateRequest request;

    @Before
    public void setup() throws Exception {
        manager.setStrategies(asList(strategy));
        PowerMockito.whenNew(SimpleValidationContext.class).withNoArguments().thenReturn(context);
    }

    @Test
    public void validate(){
        Mockito.when(context.hasErrors()).thenReturn(false);
        manager.validate(request);
        Mockito.verify(strategy).validate(request, context);
        Mockito.verifyNoMoreInteractions(strategy, messageBuilder);
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

        Mockito.verify(strategy).validate(request, context);
        Mockito.verify(messageBuilder).build(context);
        Mockito.verifyNoMoreInteractions(strategy, messageBuilder);
    }

}
