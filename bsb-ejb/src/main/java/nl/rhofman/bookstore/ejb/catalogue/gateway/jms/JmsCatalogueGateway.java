package nl.rhofman.bookstore.ejb.catalogue.gateway.jms;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ConfirmationType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.HeaderType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ObjectFactory;

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
        ObjectFactory factory = new ObjectFactory();
        ConfirmationType jaxbObject = factory.createConfirmationType();

        HeaderType headerType = factory.createHeaderType();
        headerType.setSender(confirmation.getSender());
        headerType.setRecipient(confirmation.getRecipient());
        headerType.setMessageId(confirmation.getMessageID());
        headerType.setCorrelationId(confirmation.getCorrelationID());
        jaxbObject.setHeader(headerType);

        ConfirmationType.Confirmation confirmation1 = factory.createConfirmationTypeConfirmation();
        confirmation1.setSuccess(confirmation.isSuccessful());
        confirmation1.setError(confirmation.getErrorMessage());
        jaxbObject.setConfirmation(confirmation1);


        // Transform to message object
        // TODO put the above into the assembler
//        ConfirmationType jaxbObject = assemblerService.toMessage(confirmationType);
        String xmlMessage = jaxbService.marshall(jaxbObject);

        try (JMSContext jmsContext = connectionFactory.createContext()) {
            Message message = jmsContext.createTextMessage(xmlMessage);
            jmsContext.createProducer().send(queue, message);
        }
    }
}
