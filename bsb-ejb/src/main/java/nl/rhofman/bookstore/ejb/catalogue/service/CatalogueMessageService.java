package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.dao.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.dao.MetadataBuilder;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.service.MessageStoreService;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.bookstore.persist.service.MessageService;
import nl.rhofman.bookstore.persist.model.Metadata;

@Dependent
public class CatalogueMessageService extends MessageStoreService {

    @Inject
    private MessageService messageService;

    @Inject
    public CatalogueMessageService(@Catalog XmlValidationService xmlValidationService,
                                   @Catalog JaxbService jaxbService,
                                   @Catalog AssemblerService assemblerService) {
        super(xmlValidationService, jaxbService, assemblerService);
    }

    @Override
    public void processMessageReceived(String xmlMessage) {
        Header header = getHeader(xmlMessage);
        String messageType = getMessageType(xmlMessage);

        // Store the message in the database
        Long storedMessageId = storeMessage(xmlMessage, messageType, header);

        // Get the (internal) object/representation for the XML-message
        Object domainObject = getDomainObject(messageType, xmlMessage);
        MessageReceived messageReceived = new MessageBuilder(header)
                .withMessageID(storedMessageId)
                .withDomainObject(domainObject)
                .build(MessageReceived.class);

        Catalogue catalogue = (Catalogue) messageReceived.getDomainObject();
        System.out.println("Catalogue received with " + catalogue.getBooks().size() + " books");

        fireEvent(messageReceived);
    }

    private Long storeMessage(String xmlMessage, String messageType, Header header) {
        // Store the XML
        Message storedMessage = messageService.saveMessage(xmlMessage);
        // Store the message metadata
        Metadata metadata = new MetadataBuilder(header)
                .withMessageId(storedMessage.getId())
                .withMessageType(messageType)
                .withDirection("IN")
                .build();
        messageService.saveMetadata(metadata);

        return storedMessage.getId();
    }

}
