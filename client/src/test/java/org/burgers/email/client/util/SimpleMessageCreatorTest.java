package org.burgers.email.client.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleMessageCreatorTest {
    private static final String MESSAGE = "message";
    @Mock
    private Session session;
    @Mock
    private TextMessage textMessage;

    private SimpleMessageCreator creator;

    @Before
    public void setup(){
        creator = new SimpleMessageCreator(MESSAGE);
    }

    @Test
    public void createMessage() throws JMSException {
        when(session.createTextMessage(MESSAGE)).thenReturn(textMessage);
        assertEquals(textMessage, creator.createMessage(session));
    }

}
