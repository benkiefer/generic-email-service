#Generic Email Service
There are a number of services that it seems like everyone has to write. I'd rather write it once and let everyone else steal it, tweak it (if necessary), and use it for there own evil purposes.

This project's going to be a simple queue-based email service. You point it at your email server, hook it to a queue, and send it XML messages with the appropriate metadata.

The project is a Maven-generated WAR - so you can either deploy it directly with your templates in the template directory, check out the source and hack it yourself, or you can overlay it by depending on the war and overlaying the appropriate files.

#Todo
 - Create custom namespace to simplify client use.
 - Camel route for message processing
 - Populate templates with data from request
 - Validation
 - Find an email server for testing
 - Error Handling
 - Logging
 - License

##Generating the Client Beans

The simplest way to create the client is to use the custom namespace provided.

    <email:client
        destination="myQueueName"
        connectionFactory-ref="connectionFactory"/>

You can also provide an optional "id" attribute which will override the default value of "genericEmailServiceClient".

    <email:client id="myId"
        destination="myQueueName"
        connectionFactory-ref="connectionFactory"/>

As you've probably guessed, you need to provide the name of the queue/topic you want to send to, and a reference to the bean name of your "java.jms.ConnectionFactory". If you are having trouble, an example in an integration test is forthcoming. All generated beans are namespaced under the client id to avoid collisions.


