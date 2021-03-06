package org.burgers.email.service;

import org.burgers.email.client.EmailTemplateRequest;
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
    private EmailTemplateRequest emailTemplateRequest;


    @Before
    public void setup(){
        factory = new SimpleMailMessageFactory();
        emailTemplateRequest = new EmailTemplateRequest();
        emailTemplateRequest.setFrom(FROM);
        emailTemplateRequest.setTo(asList(TO));
        emailTemplateRequest.setBcc(asList(BCC));
        emailTemplateRequest.setCc(asList(CC));
        emailTemplateRequest.setSubject(SUBJECT);
    }

    @Test
    public void build(){
        SimpleMailMessage result = factory.build(BODY, emailTemplateRequest);
        assertEquals(SUBJECT, result.getSubject());
        assertEquals(FROM, result.getFrom());
        assertEquals(BODY, result.getText());
        assertEquals(TO, asList(result.getTo()).iterator().next());
        assertEquals(BCC, asList(result.getBcc()).iterator().next());
        assertEquals(CC, asList(result.getCc()).iterator().next());

    }



}
