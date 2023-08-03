package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.dao.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.dao.MetadataMapper;
import nl.rhofman.bookstore.ejb.message.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.service.MessageStoreService;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.bookstore.persist.service.MessageService;
import nl.rhofman.bookstore.persist.model.MessageMetadata;

import java.time.LocalDateTime;

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
        System.out.println("[MessageService] - Incoming catalog message");

        MessageBuilder messageBuilder = assembleMessage(xmlMessage).withMessageID(1L);
        MessageReceived messageReceived = messageBuilder.build(MessageReceived.class);

        // Store the message (TODO maak hier een builder van of maak service in MessageService aan die message en metadata vastlegd)
        Message message = messageService.saveMessage(xmlMessage);
        MessageMetadata messageMetadata = MetadataMapper.instance()
                        .mapToMessageMetadata(messageReceived.getMessageMetadata());
        messageMetadata.setMessageId(message.getId());
        messageMetadata.setDirection("IN");
        messageMetadata.setProcessed(LocalDateTime.now());
        // TODO Store the message

        Catalogue catalogue = (Catalogue) messageReceived.getDomainObject();
        System.out.println("[MessageService] - Message type: " + messageReceived.getMessageType());
        System.out.println("[MessageService] - Metadata: " + messageReceived.getMessageMetadata().toString());
        System.out.println("[MessageService] - Number of books: " + (catalogue == null ? "Unknown" : catalogue.getBooks().size()));

        fireEvent(messageReceived);
    }

}
