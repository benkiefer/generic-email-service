package org.burgers.email.service;

import org.burgers.email.client.TemplateEmailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleMailMessageFactory {

    public SimpleMailMessage build(String text, TemplateEmailRequest templateEmailRequest){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(templateEmailRequest.getSubject());
        message.setFrom(templateEmailRequest.getFrom());
        message.setBcc(toStringArray(templateEmailRequest.getBcc()));
        message.setCc(toStringArray(templateEmailRequest.getCc()));
        message.setTo(toStringArray(templateEmailRequest.getTo()));
        message.setText(text);

        return message;
    }

    private String[] toStringArray(List<String> list){
        return list.toArray(new String[list.size()]);
    }
}
