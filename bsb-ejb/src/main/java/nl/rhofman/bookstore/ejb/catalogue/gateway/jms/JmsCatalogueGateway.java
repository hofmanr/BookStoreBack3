package nl.rhofman.bookstore.ejb.catalogue.gateway.jms;

import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ConfirmationType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ObjectFactory;

public class JmsCatalogueGateway implements CatalogueGateway {

    private final Queue queue;
    private final JMSContext jmsContext;

    private final JaxbService jaxbService;
    private final AssemblerService assemblerService;

    public JmsCatalogueGateway(Queue queue, JMSContext jmsContext, JaxbService jaxbService, AssemblerService assemblerService) {
        this.queue = queue;
        this.jmsContext = jmsContext;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
    }

    @Override
    public void sendConfirmation(Confirmation confirmation) {
        // Transform DTO-object to JAXB-object
        ConfirmationType jaxbObject = assemblerService.toMessage(confirmation);

        // Transform JAXB-object to (XML) string
        ObjectFactory factory = new ObjectFactory();
        JAXBElement<ConfirmationType> jaxbElement = factory.createConfirmation(jaxbObject);
        String xmlMessage = jaxbService.marshall(jaxbElement);

        Message message = jmsContext.createTextMessage(xmlMessage);
        jmsContext.createProducer().send(queue, message);
    }
}
