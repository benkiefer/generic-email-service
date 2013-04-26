package org.burgers.email.integration;

import com.thoughtworks.xstream.XStream;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.burgers.email.client.EmailRequestClient;
import org.burgers.email.client.TemplateEmailRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/integration-test-context.xml"})
public class TemplateRequestTest {
    private static final String TO = "to@test.com";
    private static final String FROM = "from@test.com";
    private static final String SUBJECT = "this is a subject";
    public static final String TEMPLATE_NAME = "test.template";

    @Autowired
    private EmailRequestClient emailRequestClient;

    protected Wiser mailServer;

    @Before
    public void setup() {
        mailServer = new Wiser();
        mailServer.start();
    }

    @Test
    public void send_message() throws MessagingException, IOException, InterruptedException {
        emailRequestClient.send(createTemplateRequest());

        Thread.sleep(10000);

        List<WiserMessage> messages = mailServer.getMessages();

        assertEquals(1, messages.size());

        MimeMessage message = messages.get(0).getMimeMessage();

        String content = (String) message.getContent();
        assertTrue(content.contains("My name is Testing"));
        assertEquals(TO, asList(message.getHeader("To")).get(0));
        assertEquals(FROM, asList(message.getHeader("From")).get(0));
        assertEquals(SUBJECT, message.getSubject());
    }

    private TemplateEmailRequest createTemplateRequest() {
        TemplateEmailRequest request = new TemplateEmailRequest();
        request.setSubject(SUBJECT);
        request.setTo(asList(TO));
        request.setFrom(FROM);
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put("name", "Testing");
        request.setTemplateName(TEMPLATE_NAME);
        request.setPropertyMap(propertyMap);
        return request;
    }

    @After
    public void teardown() {
        mailServer.stop();
    }

}
