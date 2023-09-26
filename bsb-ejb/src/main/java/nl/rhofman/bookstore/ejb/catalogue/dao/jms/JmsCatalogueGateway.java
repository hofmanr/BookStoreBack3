package nl.rhofman.bookstore.ejb.catalogue.dao.jms;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.catalogue.dao.CatalogueGateway;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;

public class JmsCatalogueGateway implements CatalogueGateway {

    private final Queue queue;
    private final ConnectionFactory connectionFactory;

    private final JaxbService jaxbService;
    private final AssemblerService assemblerService;

    public JmsCatalogueGateway(Queue queue, ConnectionFactory connectionFactory, JaxbService jaxbService, AssemblerService assemblerService) {
        this.queue = queue;
        this.connectionFactory = connectionFactory;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
    }

    @Override
    public void sendConfirmation(Confirmation confirmation) {
        // Transform to message object
        Object jaxbObject = assemblerService.toMessage(confirmation);
        String xmlMessage = jaxbService.marshall(jaxbObject);

        try (JMSContext jmsContext = connectionFactory.createContext()) {
            Message message = jmsContext.createTextMessage(xmlMessage);
            jmsContext.createProducer().send(queue, message);
        }
    }
}
