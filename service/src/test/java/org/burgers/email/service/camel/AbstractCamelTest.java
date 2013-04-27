package org.burgers.email.service.camel;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.ToDefinition;
import org.burgers.email.test.support.BaseEmailSupport;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/test-applicationContext.xml"})
abstract public class AbstractCamelTest extends BaseEmailSupport {
    @Value("${email.service.email.server.host}")
    private String host;

    @Value("${email.service.email.server.port}")
    private int port;

    @Autowired
    protected ModelCamelContext camelContext;

    protected ProducerTemplate producerTemplate;

    @Before
    public void preSetup() {
        producerTemplate = camelContext.createProducerTemplate();
    }

    protected void replaceRouteStart(String routeId, final String uri) {
        try {
            camelContext.getRouteDefinition(routeId).adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    replaceFromWith(uri);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void replaceFinalDestinationInRoute(String routeId, final String uri) {
        try {
            camelContext.getRouteDefinition(routeId).adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    weaveByType(ToDefinition.class).selectLast().replace().to(uri);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void replaceBeanByIdWith(String routeId, final String beanId, final Object replacementObject) {
        try {
            camelContext.getRouteDefinition(routeId).adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    weaveById(beanId).replace().bean(replacementObject);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }
}
