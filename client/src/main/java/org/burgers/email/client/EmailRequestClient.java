package org.burgers.email.client;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class EmailRequestClient {
    private ConnectionFactory connectionFactory;
    private String queueName;

    public void send(EmailRequest request){
        final String message = "";
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true);
        template.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

}
