package org.burgers.email.test.support;

import org.junit.After;
import org.junit.Before;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

abstract public class BaseEmailSupport {
    protected Wiser mailServer;

    @Before
    public void email_setup() {
        mailServer = new Wiser();
        mailServer.start();
    }

    protected void assertMessageCount(int count){
        assertEquals(count, mailServer.getMessages().size());
    }

    protected MimeMessage getMessage(int index){
        try {
            WiserMessage wiserMessage = mailServer.getMessages().get(index);
            return wiserMessage.getMimeMessage();
        } catch (MessagingException e) {
            fail("Can't convert message");
        } catch (NullPointerException e){
            fail("Message not found");
        }
        return null;
    }

    @After
    public void email_tearDown(){
        mailServer.stop();
    }

}
