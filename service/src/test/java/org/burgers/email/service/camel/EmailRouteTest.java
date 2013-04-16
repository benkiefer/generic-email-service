package org.burgers.email.service.camel;

import com.thoughtworks.xstream.XStream;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.burgers.email.client.TemplateEmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.subethamail.wiser.WiserMessage;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailRouteTest extends AbstractCamelTest {
    public static final String TO = "to@test.com";
    public static final String FROM = "from@test.com";
    public static final String SUBJECT = "subject";
    private static final String NEW_FROM_URI = "direct:testing";

    @EndpointInject(uri = "mock:failure")
    private MockEndpoint failureEndpoint;

    @EndpointInject(uri = "mock:retry")
    private MockEndpoint retryEndpoint;

    @Before
    public void setup() {
        replaceRouteStart("emailProcessing", NEW_FROM_URI);
        replaceFinalDestinationInRoute("failureRoute", failureEndpoint.getEndpointUri());
        replaceFinalDestinationInRoute("retryRoute", retryEndpoint.getEndpointUri());
    }

    @Test
    @DirtiesContext
    public void sendMessage() throws Exception {
        failureEndpoint.setExpectedMessageCount(0);

        producerTemplate.sendBody(NEW_FROM_URI, new XStream().toXML(createFakeRequest("test.template")));

        List<WiserMessage> messages = mailServer.getMessages();

        assertEquals(1, messages.size());

        MimeMessage message = messages.get(0).getMimeMessage();

        String content = (String) message.getContent();
        assertTrue(content.contains("My name is Testing"));
        assertEquals(TO, asList(message.getHeader("To")).get(0));
        assertEquals(FROM, asList(message.getHeader("From")).get(0));
        assertEquals(SUBJECT, message.getSubject());
        failureEndpoint.assertIsSatisfied();
    }

    @Test
    @DirtiesContext
    public void sendMessage_template_error() throws Exception {
        failureEndpoint.setExpectedMessageCount(1);

        producerTemplate.sendBody(NEW_FROM_URI, new XStream().toXML(createFakeRequest("kaboom")));
        assertEquals(0, mailServer.getMessages().size());

        failureEndpoint.assertIsSatisfied();
    }

    @Test
    @DirtiesContext
    public void sendMessage_smtp_connection_error() throws Exception {
        mailServer.stop();

        failureEndpoint.setExpectedMessageCount(0);
        retryEndpoint.setExpectedMessageCount(1);

        producerTemplate.sendBody(NEW_FROM_URI, new XStream().toXML(createFakeRequest("test.template")));
        assertEquals(0, mailServer.getMessages().size());

        failureEndpoint.assertIsSatisfied();
        retryEndpoint.assertIsSatisfied();
    }

    private TemplateEmailRequest createFakeRequest(String templateName){
        TemplateEmailRequest request = new TemplateEmailRequest();
        request.setTo(asList(TO));
        request.setFrom(FROM);
        request.setSubject(SUBJECT);
        request.setTemplateName(templateName);
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put("name", "Testing");
        request.setPropertyMap(propertyMap);
        return request;
    }

}
