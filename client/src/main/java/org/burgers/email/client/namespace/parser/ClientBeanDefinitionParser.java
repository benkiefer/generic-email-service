package org.burgers.email.client.namespace.parser;

import com.thoughtworks.xstream.XStream;
import org.burgers.email.client.EmailRequestClient;
import org.burgers.email.client.util.MessageBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ClientBeanDefinitionParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EmailRequestClient.class);

        String id = element.getAttribute("id");

        builder.addPropertyValue("queueName", element.getAttribute("destination"));
        builder.addPropertyReference("connectionFactory", element.getAttribute("connectionFactory-ref"));

        registerMessageBuilderFor(parserContext, builder, id);

        parserContext.registerBeanComponent(new BeanComponentDefinition(builder.getBeanDefinition(), id));
        return builder.getBeanDefinition();
    }

    private void registerMessageBuilderFor(ParserContext parserContext, BeanDefinitionBuilder clientBuilder, String clientId) {
        // add a MessageBuilder to the Client
        BeanDefinitionBuilder messageBuilderDefinition = BeanDefinitionBuilder.genericBeanDefinition(MessageBuilder.class);

        String messageBuilderId = clientId + "_messageBuilder";

        parserContext.registerBeanComponent(new BeanComponentDefinition(messageBuilderDefinition.getBeanDefinition(), messageBuilderId));

        registerXstreamFor(parserContext, messageBuilderDefinition, messageBuilderId);

        clientBuilder.addPropertyReference("messageBuilder", messageBuilderId);
    }

    private void registerXstreamFor(ParserContext parserContext, BeanDefinitionBuilder messageBuilderDefinition, String messageBuilderId) {
        // add xstream to the MessageBuilder
        BeanDefinitionBuilder xstreamDefinition = BeanDefinitionBuilder.genericBeanDefinition(XStream.class);

        String xstreamId = messageBuilderId + "_xStream";

        parserContext.registerBeanComponent(new BeanComponentDefinition(xstreamDefinition.getBeanDefinition(), xstreamId));

        messageBuilderDefinition.addPropertyReference("xStream", xstreamId);
    }

}
