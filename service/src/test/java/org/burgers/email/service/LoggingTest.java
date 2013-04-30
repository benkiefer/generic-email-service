package org.burgers.email.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LoggingTest {

    public static final String MESSAGE = "kaboom!";
    private static Log log = LogFactory.getLog(LoggingTest.class);

    @Test
    public void logging() throws IOException {
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        System.setProperty("email.service.log.file.location", file.getAbsolutePath());
        System.setProperty("email.service.log4j.config.location", "classpath:app-log4j.xml");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("contexts/test-logging-context.xml");

        log.error(MESSAGE);

        assertTrue(readContentsToString(file).contains(MESSAGE));

        context.destroy();
    }

    private String readContentsToString(File file){
        StringBuilder contents = new StringBuilder();
            try {
              BufferedReader input =  new BufferedReader(new FileReader(file));
              try {
                String line = null;
                while (( line = input.readLine()) != null){
                  contents.append(line);
                  contents.append(System.getProperty("line.separator"));
                }
              }
              finally {
                input.close();
              }
            }

            catch (IOException ex){
              fail(ex.getMessage());
            }

            return contents.toString();
    }

}
