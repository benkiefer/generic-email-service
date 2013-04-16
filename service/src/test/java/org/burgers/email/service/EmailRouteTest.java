package org.burgers.email.service;

import com.thoughtworks.xstream.XStream;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.burgers.email.client.TemplateEmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/test-applicationContext.xml"})
public class EmailRouteTest {
    @Autowired
    private ModelCamelContext camelContext;
    @Autowired
    private Wiser mailServer;

    @EndpointInject(ref="emailIn")
    private Endpoint queueIn;

    private ProducerTemplate template;

    @Before
    public void setup() throws Exception {
        template = camelContext.createProducerTemplate();
    }

    @Test
    public void sendMessage() throws Exception {
        template.sendBody(queueIn, new XStream().toXML(createFakeRequest()));

        Thread.sleep(1000);

        List<WiserMessage> messages = mailServer.getMessages();

        assertEquals(1, messages.size());

        MimeMessage message = messages.get(0).getMimeMessage();

        String content = (String) message.getContent();
        assertTrue(content.contains("My name is Testing"));
        assertEquals("to@test.com", asList(message.getHeader("To")).get(0));
        assertEquals("from@test.com", asList(message.getHeader("From")).get(0));
        assertEquals("subject", message.getSubject());
    }

    private TemplateEmailRequest createFakeRequest(){
        TemplateEmailRequest request = new TemplateEmailRequest();
        request.setTo(asList("to@test.com"));
        request.setFrom("from@test.com");
        request.setSubject("subject");
        request.setTemplateName("test.template");
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put("name", "Testing");
        request.setPropertyMap(propertyMap);
        return request;
    }

}
