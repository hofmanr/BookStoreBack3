package nl.rhofman.bookstore.ejb.catalogue.gateway.jms;

import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.message.domain.Message;

public class JmsCatalogueGateway implements CatalogueGateway {

    private final Queue queue;
    private final JMSContext jmsContext;

    public JmsCatalogueGateway(Queue queue, JMSContext jmsContext) {
        this.queue = queue;
        this.jmsContext = jmsContext;
    }

    @Override
    public void sendConfirmation(Message message) {
        String xmlMessage = message.getXml();
        jmsContext.createProducer().send(queue, jmsContext.createTextMessage(xmlMessage));
    }
}
