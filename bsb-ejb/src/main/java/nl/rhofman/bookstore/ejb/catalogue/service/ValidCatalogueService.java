package nl.rhofman.bookstore.ejb.catalogue.service;


import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.event.MessageProcessed;
import nl.rhofman.bookstore.ejb.message.event.MessageValidated;
import nl.rhofman.bookstore.ejb.validate.domain.Valid;
import nl.rhofman.bookstore.persist.service.MessageService;

@Dependent
public class ValidCatalogueService {

    @Inject
    private MessageService messageService;

    @Inject
    @Valid
    @DomainType(Catalogue.class)
    private Event<MessageProcessed> processedEvent;

    public void processMessageValidatedEvent(@Observes @Valid @DomainType(Catalogue.class) MessageValidated messageValidated) {
        System.out.println("ValidCatalogueService");
        Long messageID = messageValidated.getMessageID();
        Catalogue catalogue = (Catalogue) messageValidated.getDomainObject();
        Header header = messageValidated.getHeader();
        catalogue.getBooks().forEach(System.out::println);

        // 1. Save message metadata
        // TODO messageService.saveMetadata(messageID, "BOOKS", "IN", header);

        // 2. process message...
        // TODO

        // 3. return confirmation to sender
        MessageProcessed messageProcessed = new MessageProcessed(messageID,
                messageValidated.getMessageType(),
                header,
                catalogue);
        processedEvent.fire(messageProcessed);
    }
}
