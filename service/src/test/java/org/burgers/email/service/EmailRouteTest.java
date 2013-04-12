package org.burgers.email.service;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/test-applicationContext.xml"})
public class EmailRouteTest {
    @Autowired
    private CamelContext camelContext;

    @Test
    public void doSomething() {
        System.out.println(camelContext.getName());
    }
}
