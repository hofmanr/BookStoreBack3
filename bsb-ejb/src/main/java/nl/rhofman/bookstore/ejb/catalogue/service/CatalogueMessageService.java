package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.message.service.MessageService;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.message.service.MessageReceivedService;

@Dependent
public class CatalogueMessageService extends MessageReceivedService {

    @Inject
    private MessageService messageService;

    @Inject
    @Catalog
    private MessageBuilder messageBuilder;

    @Override
    public void processMessageReceived(String xmlMessage) {
        Message message = messageBuilder.incoming().withXml(xmlMessage).build();

        // Store the message
        messageService.persist(message);

        Catalogue catalogue = message.getDomainObject();
        System.out.println("Catalogue received with " + catalogue.getBooks().size() + " books");

        fireEvent(new MessageReceived(message));
    }

}
