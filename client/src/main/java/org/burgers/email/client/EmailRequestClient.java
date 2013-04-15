package org.burgers.email.client;

import org.burgers.email.client.util.GenericClient;

public class EmailRequestClient extends GenericClient {
    private String queueName;

    public void send(TemplateEmailRequest templateEmailRequest){
        sendMessage(templateEmailRequest);
    }

    @Override
    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
