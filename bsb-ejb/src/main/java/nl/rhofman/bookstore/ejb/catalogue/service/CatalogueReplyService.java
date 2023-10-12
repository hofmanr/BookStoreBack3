package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.domain.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.event.MessageProcessed;
import nl.rhofman.bookstore.ejb.message.service.MessageService;
import nl.rhofman.bookstore.ejb.validate.domain.Invalid;
import nl.rhofman.bookstore.ejb.validate.domain.Valid;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.xml.Catalog;

import java.util.Collections;
import java.util.List;

@Dependent
public class CatalogueReplyService {

    @Inject
    private CatalogueGateway catalogueGateway;

    @Inject
    @Catalog
    private MessageBuilder messageBuilder;

    @Inject
    private MessageService messageService;

    public void processInvalidMessageProcessedEvent(@Observes @Invalid @DomainType(Catalogue.class) MessageProcessed messageProcessed) {
        Message message = messageProcessed.getMessage();
        List<ValidationResult> validationResults = messageProcessed.getValidationResults();
        sendConfirmation(message, validationResults);
    }

    public void processValidMessageProcessedEvent(@Observes @Valid @DomainType(Catalogue.class) MessageProcessed messageProcessed) {
        Message message = messageProcessed.getMessage();
        sendConfirmation(message);
    }

    private void sendConfirmation(Message incomingMessage) {
        sendConfirmation(incomingMessage, Collections.emptyList());
    }

    private void sendConfirmation(Message receivedMessage, List<ValidationResult> validationResults) {
        Confirmation confirmation = new Confirmation.ConfirmationBuilder(receivedMessage.getDomainObject())
                .withValidationResults(validationResults)
                .build();
        Message outgoingMessage = messageBuilder.outgoing()
                .withDomainObject(confirmation)
                .withReceivedMessage(receivedMessage)
                .build();

        // Store the message
        messageService.persist(outgoingMessage);

        catalogueGateway.sendConfirmation(outgoingMessage);
    }

}
