package org.burgers.email.client;

import org.burgers.email.client.util.MessageBuilder;
import org.burgers.email.client.util.SimpleMessageCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EmailRequestClient.class)
public class EmailRequestClientTest {
    private static final String QUEUE_NAME = "queueName";
    private static final String MESSAGE = "message";
    @Mock
    private MessageBuilder messageBuilder;
    @Mock
    private ConnectionFactory connectionFactory;
    @Mock
    private TemplateEmailRequest templateEmailRequest;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private SimpleMessageCreator simpleMessageCreator;
    @InjectMocks
    private EmailRequestClient client = new EmailRequestClient();

    @Before
    public void setup() throws Exception {
        when(messageBuilder.build(templateEmailRequest)).thenReturn(MESSAGE);
        client.setQueueName(QUEUE_NAME);
        PowerMockito.whenNew(JmsTemplate.class).withArguments(connectionFactory).thenReturn(jmsTemplate);
        PowerMockito.whenNew(SimpleMessageCreator.class).withArguments(MESSAGE).thenReturn(simpleMessageCreator);
    }

    @Test
    public void sendMessage(){
        client.send(templateEmailRequest);
        verify(jmsTemplate).setPubSubDomain(true);
        verify(jmsTemplate).setDefaultDestinationName(QUEUE_NAME);
        verify(jmsTemplate).send(simpleMessageCreator);
    }

}
