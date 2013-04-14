package org.burgers.email.client.util;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class SimpleMessageCreator implements MessageCreator {
    private String message;

    public SimpleMessageCreator(String message) {
        this.message = message;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(message);
    }
}
