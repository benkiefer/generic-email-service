package org.burgers.email.service;

import org.burgers.email.client.EmailTemplateRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleMailMessageFactory {

    public SimpleMailMessage build(String text, EmailTemplateRequest emailTemplateRequest){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(emailTemplateRequest.getSubject());
        message.setFrom(emailTemplateRequest.getFrom());
        message.setBcc(toStringArray(emailTemplateRequest.getBcc()));
        message.setCc(toStringArray(emailTemplateRequest.getCc()));
        message.setTo(toStringArray(emailTemplateRequest.getTo()));
        message.setText(text);

        return message;
    }

    private String[] toStringArray(List<String> list){
        return list.toArray(new String[list.size()]);
    }
}
