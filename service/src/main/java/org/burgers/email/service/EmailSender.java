package org.burgers.email.service;

import org.burgers.email.client.TemplateEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailContentBuilder builder;

    @Autowired
    private SimpleMailMessageFactory factory;

    public void sendMessage(TemplateEmailRequest templateEmailRequest){
        String messageBody = builder.buildFrom(templateEmailRequest.getTemplateName(), templateEmailRequest.getPropertyMap());

        SimpleMailMessage message = factory.build(messageBody, templateEmailRequest);

        javaMailSender.send(message);
    }
}
