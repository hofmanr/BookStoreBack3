package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageProcessed;
import nl.rhofman.bookstore.ejb.message.event.MessageValidated;
import nl.rhofman.bookstore.ejb.validate.domain.Invalid;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.util.List;

@Dependent
public class InvalidCatalogueService {

    @Inject
    @Invalid
    @DomainType(Catalogue.class)
    private Event<MessageProcessed> processedEvent;

    public void processMessageValidatedEvent(@Observes @Invalid @DomainType(Catalogue.class) MessageValidated messageValidated) {
        System.out.println("InvalidCatalogueService");
        Message message = messageValidated.getMessage();
        List<ValidationResult> invalidResults = messageValidated.getInvalidValidationResults();
        invalidResults.forEach(System.out::println);

        MessageProcessed messageProcessed = new MessageProcessed(message, invalidResults);
        processedEvent.fire(messageProcessed);
    }

}
