package org.burgers.email.client.util;

import com.thoughtworks.xstream.XStream;

public class MessageBuilder {

    private XStream xStream;

    public String build(Object object){
        return xStream.toXML(object);
    }

    public void setxStream(XStream xStream) {
        this.xStream = xStream;
    }

    public XStream getxStream() {
        return xStream;
    }
}
