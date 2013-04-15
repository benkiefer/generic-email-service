package org.burgers.email.service;

import org.burgers.email.client.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class EmailSender {
    public static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    public void sendMessage(EmailRequest emailRequest){
        System.out.println(emailRequest);
    }
}
