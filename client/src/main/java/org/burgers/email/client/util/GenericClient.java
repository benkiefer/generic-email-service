package org.burgers.email.client.util;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

abstract public class GenericClient {
    private MessageBuilder messageBuilder;
    private ConnectionFactory connectionFactory;

    protected void sendMessage(Object request){
        final String message = messageBuilder.build(request);
        JmsTemplate template = new JmsTemplate(connectionFactory);

        template.send(getQueueName(), new SimpleMessageCreator(message));
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setMessageBuilder(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    abstract public String getQueueName();

    public MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
