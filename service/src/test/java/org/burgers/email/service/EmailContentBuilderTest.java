package org.burgers.email.service;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EmailContentBuilder.class)
public class EmailContentBuilderTest {
    public static final String TEMPLATE_NAME = "template";
    public static final String MESSAGE = "message";
    private Map<String, String> map;
    @Mock
    private Configuration configuration;
    @Mock
    private Template template;
    @InjectMocks
    private EmailContentBuilder builder = new EmailContentBuilder();
    @Mock
    private StringWriter stringWriter;

    @Before
    public void setup() throws Exception {
        map = new HashMap<String, String>();
        PowerMockito.whenNew(StringWriter.class).withNoArguments().thenReturn(stringWriter);
    }

    @Test
    public void happy_path() throws IOException, TemplateException {
        when(configuration.getTemplate(TEMPLATE_NAME)).thenReturn(template);
        when(stringWriter.toString()).thenReturn(MESSAGE);
        assertEquals(MESSAGE, builder.buildFrom(TEMPLATE_NAME, map));
        verify(template).process(map, stringWriter);
    }

    @Test(expected = TemplateProcessingException.class)
    public void io_exception() throws IOException {
        doThrow(new IOException()).when(configuration).getTemplate(TEMPLATE_NAME);

        builder.buildFrom(TEMPLATE_NAME, map);
    }

    @Test(expected = TemplateProcessingException.class)
    public void template_exception() throws IOException, TemplateException {
        when(configuration.getTemplate(TEMPLATE_NAME)).thenReturn(template);
        doThrow(new TemplateException(Environment.getCurrentEnvironment())).when(template).process(map, stringWriter);

        builder.buildFrom(TEMPLATE_NAME, map);
    }

}
