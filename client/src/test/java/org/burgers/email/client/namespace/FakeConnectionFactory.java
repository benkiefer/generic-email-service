package org.burgers.email.client.namespace;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class FakeConnectionFactory implements ConnectionFactory {
    @Override
    public Connection createConnection() throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection(String s, String s2) throws JMSException {
        return null;
    }
}
