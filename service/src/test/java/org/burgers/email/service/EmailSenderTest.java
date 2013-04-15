package org.burgers.email.service;

import org.burgers.email.client.TemplateEmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {
    public static final String MESSAGE = "message";
    public static final String TEMPLATE = "template";

    @InjectMocks
    private EmailSender emailSender;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private SimpleMailMessageFactory factory;
    @Mock
    private EmailContentBuilder builder;
    @Mock
    private TemplateEmailRequest templateEmailRequest;
    @Mock
    private SimpleMailMessage simpleMessage;

    private Map<String, String> properties;

    @Before
    public void setup(){
        properties = new HashMap<String, String>();
        when(templateEmailRequest.getPropertyMap()).thenReturn(properties);
        when(templateEmailRequest.getTemplateName()).thenReturn(TEMPLATE);
    }

    @Test
    public void sendMessage(){
        when(builder.buildFrom(TEMPLATE, properties)).thenReturn(MESSAGE);
        when(factory.build(MESSAGE, templateEmailRequest)).thenReturn(simpleMessage);
        emailSender.sendMessage(templateEmailRequest);
        verify(javaMailSender).send(simpleMessage);
    }


}
