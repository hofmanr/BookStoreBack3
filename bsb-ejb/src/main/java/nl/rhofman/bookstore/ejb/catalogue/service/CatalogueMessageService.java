package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.dao.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.event.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.service.MessageStoreService;

@Dependent
public class CatalogueMessageService extends MessageStoreService {

    @Inject
    public CatalogueMessageService(@Catalog XmlValidationService xmlValidationService,
                                   @Catalog JaxbService jaxbService,
                                   @Catalog AssemblerService assemblerService) {
        super(xmlValidationService, jaxbService, assemblerService);
    }

    @Override
    public void processMessageReceived(String xmlMessage) {
        System.out.println("[MessageService] - Incoming catalog message");

        // TODO Store the message....

        MessageBuilder messageBuilder = assembleMessage(xmlMessage).withMessageID(1L);
        MessageReceived messageReceived = messageBuilder.build(MessageReceived.class);

        Catalogue catalogue = (Catalogue) messageReceived.getDomainObject();
        System.out.println("[MessageService] - Message type: " + messageReceived.getMessageType());
        System.out.println("[MessageService] - Metadata: " + messageReceived.getMessageMetadata().toString());
        System.out.println("[MessageService] - Number of books: " + (catalogue == null ? "Unknown" : catalogue.getBooks().size()));

        fireEvent(messageReceived);
    }

}
