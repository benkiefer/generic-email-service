##Generic Email Service

[![Build Status](https://secure.travis-ci.org/benkiefer/generic-email-service.png?branch=master)](http://travis-ci.org/benkiefer/generic-email-service)

This project is a simple queue-based email service. You point it at your email server, hook it to a queue, and send it XML messages with the appropriate metadata.

The project is a Maven-generated WAR so you can:
 - deploy it directly with your templates in the template directory,
 - check out the source and hack it yourself, or
 - depend on the war and overlay the appropriate files.

##Using the Client

The simplest way to create the client is to use the custom namespace provided in your XML application context.

    <email:client
        destination="myQueueName"
        connectionFactory-ref="connectionFactory"/>

You can also provide an optional "id" attribute which will override the default id of "genericEmailServiceClient".

    <email:client id="myId"
        destination="myQueueName"
        connectionFactory-ref="connectionFactory"/>

All generated beans are name-spaced under the client id to avoid collisions.

As you've probably guessed, you need to provide the name of the queue/topic you want to send to, and a reference to the bean name of your "java.jms.ConnectionFactory". If you are having trouble, check out the EmailTemplateRequestTest class in the Integration module.