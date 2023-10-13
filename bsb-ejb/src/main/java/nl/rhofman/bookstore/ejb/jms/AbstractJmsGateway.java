package nl.rhofman.bookstore.ejb.jms;

import jakarta.jms.Destination;
import jakarta.jms.JMSContext;

public abstract class AbstractJmsGateway {

    protected void sendMessage(JMSContext jmsContext, Destination destination, String xmlMessage) {
        jmsContext.createProducer()
                .send(destination, jmsContext.createTextMessage(xmlMessage));
    }
}
