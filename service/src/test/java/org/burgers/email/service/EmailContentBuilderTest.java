package org.burgers.email.service;

import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:contexts/test-template-loading-context.xml"})
public class EmailContentBuilderTest {
    public static final String TEMPLATE_NAME = "test.template";

    @Autowired
    private EmailContentBuilder builder;

    private Map<String, String> properties;

    @Before
    public void setup(){
        properties = new HashMap<String, String>();
    }

    @Test
    public void buildFrom() throws IOException, TemplateException {
        properties.put("name", "Testing");
        String result = builder.buildFrom(TEMPLATE_NAME, properties);
        assertTrue(result.contains("My name is Testing."));
    }

}
