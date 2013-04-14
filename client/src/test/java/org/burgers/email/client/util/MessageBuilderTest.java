package org.burgers.email.client.util;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageBuilderTest {
    private static final String REQUEST = "request";
    private static final String MESSAGE = "message";

    @InjectMocks
    private MessageBuilder messageBuilder;
    @Mock
    private XStream xstream;

    @Test
    public void build(){
        when(xstream.toXML(REQUEST)).thenReturn(MESSAGE);
        String result = messageBuilder.build(REQUEST);
        assertEquals(result, MESSAGE);
    }

}
