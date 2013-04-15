package org.burgers.email.client.namespace;

import com.thoughtworks.xstream.XStream;
import org.burgers.email.client.EmailRequestClient;
import org.burgers.email.client.util.MessageBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanCreationTest extends BeanDefinitionParserHelper {

    @Before
    public void setup() {
        cleanContext();
    }

    @Test
    public void contextLoadTest_default_bean_id() {
        prepareContext("<email:client destination=\"myQueueName\" connectionFactory-ref=\"fakeConnectionFactory\"/>");

        EmailRequestClient client = (EmailRequestClient) context.getBean("genericEmailServiceClient");
        FakeConnectionFactory connectionFactory = (FakeConnectionFactory) context.getBean("fakeConnectionFactory");
        MessageBuilder messageBuilder = (MessageBuilder) context.getBean("genericEmailServiceClient_messageBuilder");
        XStream xstream = (XStream) context.getBean("genericEmailServiceClient_messageBuilder_xStream");

        assertNotNull(client);
        assertEquals("myQueueName", client.getQueueName());
        assertEquals(connectionFactory, client.getConnectionFactory());
        assertEquals(messageBuilder, client.getMessageBuilder());
        assertEquals(xstream, messageBuilder.getxStream());
    }

    @Test
    public void contextLoadTest_custom_bean_id() {
        prepareContext("<email:client id=\"myCustomId\" destination=\"myQueueName\" connectionFactory-ref=\"fakeConnectionFactory\"/>");

        EmailRequestClient client = (EmailRequestClient) context.getBean("myCustomId");
        MessageBuilder messageBuilder = (MessageBuilder) context.getBean("myCustomId_messageBuilder");

        FakeConnectionFactory connectionFactory = (FakeConnectionFactory) context.getBean("fakeConnectionFactory");
        XStream xstream = (XStream) context.getBean("myCustomId_messageBuilder_xStream");

        assertNotNull(client);
        assertEquals("myQueueName", client.getQueueName());
        assertEquals(connectionFactory, client.getConnectionFactory());
        assertEquals(messageBuilder, client.getMessageBuilder());
        assertEquals(xstream, messageBuilder.getxStream());
    }


}
