package org.burgers.email.service;

import com.thoughtworks.xstream.XStream;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.burgers.email.client.TemplateEmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.util.Arrays.asList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/test-applicationContext.xml"})
public class EmailRouteTest {
    @Autowired
    private CamelContext camelContext;

    private ProducerTemplate template;

    @EndpointInject(uri="jms:queue:Email.Service.Errors")
    private Endpoint queueIn;

    @EndpointInject(uri="direct:in")
    private Endpoint blahIn;

    @Before
    public void setup(){
        template = camelContext.createProducerTemplate();
    }

    @Test
    public void doSomething() throws Exception {
        template.sendBody(blahIn, new XStream().toXML(createFakeRequest()));
        camelContext.start();
    }

    private TemplateEmailRequest createFakeRequest(){
        TemplateEmailRequest request = new TemplateEmailRequest();
        request.setTo(asList("to@test.com"));
        request.setFrom("from@test.com");
        request.setSubject("subject");
        request.setTemplateName("template");
        return request;
    }
}
