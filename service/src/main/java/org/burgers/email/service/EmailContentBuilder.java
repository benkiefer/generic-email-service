package org.burgers.email.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
public class EmailContentBuilder {
    private static final String DEFAULT_MESSAGE = "Unable to load templates. Check configuration.";
    @Autowired
    private Configuration configuration;

    public String buildFrom(String templateName, Map<String, String> properties) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            template.process(properties, writer);
            return writer.toString();
        } catch (IOException e) {
            throw new TemplateProcessingException(DEFAULT_MESSAGE, e);
        } catch (TemplateException e){
            throw new TemplateProcessingException(DEFAULT_MESSAGE, e);
        }
    }

}
