##Generic Email Service
This project is a simple queue-based email service. You point it at your email server, hook it to a queue, and send it XML messages with the appropriate metadata.

The project is a Maven-generated WAR so you can:
 - deploy it directly with your templates in the template directory,
 - check out the source and hack it yourself, or
 - depend on the war and overlay the appropriate files.

##Todo
 - Error Handling
 - License
 - Property descriptions
 - End to end integration tests at the container level

##Generating the Client

The simplest way to create the client is to use the custom namespace provided in your XML application context.

    <email:client
        destination="myQueueName"
        connectionFactory-ref="connectionFactory"/>

You can also provide an optional "id" attribute which will override the default value of "genericEmailServiceClient".

    <email:client id="myId"
        destination="myQueueName"
        connectionFactory-ref="connectionFactory"/>

As you've probably guessed, you need to provide the name of the queue/topic you want to send to, and a reference to the bean name of your "java.jms.ConnectionFactory". If you are having trouble, an example in an integration test is forthcoming. All generated beans are namespaced under the client id to avoid collisions.gst