#Generic Email Service
There are a number of services that it seems like everyone has to write. I'd rather write it once and let everyone else steal it, tweak it (if necessary), and use it for there own evil purposes.

This projects is going to be a simple queue-based email service. You point it at your email server, hook it to a queue, and send it messages with the appropriate metadata.

The project is a Maven-generated WAR - so you can either deploy it directly with your templates in the template directory, check out the source and hack it yourself, or you can overlay it by depending on the war and overlaying the appropriate classes.

#Todo
 - Establish email request contract
 - Camel route for message processing
 - Populate templates with data from request
 - Validation
 - Find an email server for testing
 - Error Handling
 - Logging
 - License

