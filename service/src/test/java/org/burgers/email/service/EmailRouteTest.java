package org.burgers.email.service;

import com.thoughtworks.xstream.XStream;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.ToDefinition;
import org.burgers.email.client.TemplateEmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
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
    public static final String TO = "to@test.com";
    public static final String FROM = "from@test.com";
    public static final String SUBJECT = "subject";
    private static final String NEW_FROM_URI = "direct:testing";

    @EndpointInject(uri = "mock:result")
    private MockEndpoint errorEndpoint;

    @Autowired
    private ModelCamelContext camelContext;

    @Autowired
    private Wiser mailServer;

    private ProducerTemplate template;

    @Before
    public void setup() throws Exception {
        template = camelContext.createProducerTemplate();
        camelContext.getRouteDefinition("emailProcessing").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith(NEW_FROM_URI);
            }
        });

        camelContext.getRouteDefinition("failureRoute").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveByType(ToDefinition.class).selectLast().replace().to(errorEndpoint);
            }
        });
    }

    @Test
    @DirtiesContext
    public void sendMessage() throws Exception {
        errorEndpoint.setExpectedMessageCount(0);

        template.sendBody(NEW_FROM_URI, new XStream().toXML(createFakeRequest("test.template")));

        List<WiserMessage> messages = mailServer.getMessages();

        assertEquals(1, messages.size());

        MimeMessage message = messages.get(0).getMimeMessage();

        String content = (String) message.getContent();
        assertTrue(content.contains("My name is Testing"));
        assertEquals(TO, asList(message.getHeader("To")).get(0));
        assertEquals(FROM, asList(message.getHeader("From")).get(0));
        assertEquals(SUBJECT, message.getSubject());
        errorEndpoint.assertIsSatisfied();
    }

    @Test
    @DirtiesContext
    public void sendMessage_error() throws Exception {
        errorEndpoint.setExpectedMessageCount(1);

        template.sendBody(NEW_FROM_URI, new XStream().toXML(createFakeRequest("kaboom")));
        assertEquals(0, mailServer.getMessages().size());

        errorEndpoint.assertIsSatisfied();
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
