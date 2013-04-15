package org.burgers.email.service;

import org.burgers.email.client.TemplateEmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SimpleMailMessageFactoryTest {
    public static final String BODY = "body";
    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String BCC = "bcc";
    public static final String CC = "cc";
    public static final String SUBJECT = "subject";
    private SimpleMailMessageFactory factory;
    private TemplateEmailRequest templateEmailRequest;


    @Before
    public void setup(){
        factory = new SimpleMailMessageFactory();
        templateEmailRequest = new TemplateEmailRequest();
        templateEmailRequest.setFrom(FROM);
        templateEmailRequest.setTo(asList(TO));
        templateEmailRequest.setBcc(asList(BCC));
        templateEmailRequest.setCc(asList(CC));
        templateEmailRequest.setSubject(SUBJECT);
    }

    @Test
    public void build(){
        SimpleMailMessage result = factory.build(BODY, templateEmailRequest);
        assertEquals(SUBJECT, result.getSubject());
        assertEquals(FROM, result.getFrom());
        assertEquals(BODY, result.getText());
        assertEquals(TO, asList(result.getTo()).iterator().next());
        assertEquals(BCC, asList(result.getBcc()).iterator().next());
        assertEquals(CC, asList(result.getCc()).iterator().next());

    }



}
