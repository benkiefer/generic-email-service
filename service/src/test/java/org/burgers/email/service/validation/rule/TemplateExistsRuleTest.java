package org.burgers.email.service.validation.rule;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.burgers.email.service.validation.ValidationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TemplateExistsRuleTest {
    public static final String TEMPLATE = "template";
    public static final String FIELD = "field";

    @InjectMocks
    private TemplateExistsRule rule;
    @Mock
    private ValidationContext context;
    @Mock
    private Configuration configuration;
    @Mock
    private Template template;

    @Test
    public void valid() throws IOException {
        Mockito.when(configuration.getTemplate(TEMPLATE)).thenReturn(template);
        assertTrue(rule.validate(TEMPLATE, FIELD, context));
        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void not_valid() throws IOException {
        Mockito.doThrow(new IOException()).when(configuration).getTemplate(TEMPLATE);
        assertFalse(rule.validate(TEMPLATE, FIELD, context));

        Mockito.verify(context).error(FIELD, String.format(TemplateExistsRule.MESSAGE, TEMPLATE));
        Mockito.verifyNoMoreInteractions(context);
    }

}
