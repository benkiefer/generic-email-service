package org.burgers.email.client.namespace;

import org.burgers.email.client.namespace.parser.ClientBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class EmailNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("client", new ClientBeanDefinitionParser());
    }
}
