package nl.rhofman.bookstore.ejb.catalogue.gateway.jms;

import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.jms.AbstractJmsGateway;
import nl.rhofman.bookstore.ejb.message.domain.Message;

public class JmsCatalogueGateway extends AbstractJmsGateway implements CatalogueGateway {

    private final Queue queue;
    private final JMSContext jmsContext;

    public JmsCatalogueGateway(Queue queue, JMSContext jmsContext) {
        this.queue = queue;
        this.jmsContext = jmsContext;
    }

    @Override
    public void sendConfirmation(Message message) {
        sendMessage(jmsContext, queue, message.getXml());
    }
}
