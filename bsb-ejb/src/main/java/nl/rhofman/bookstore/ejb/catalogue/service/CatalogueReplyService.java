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
import java.util.UUID;

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

    private void sendConfirmation(Message incomingMessage, List<ValidationResult> validationResults) {
        Confirmation confirmation = constructConfirmation(incomingMessage, validationResults);

        Message outgoingMessage = messageBuilder.outgoing()
                .withDomainObject(confirmation)
                .build();

        // Store the message
        messageService.persist(outgoingMessage);

        catalogueGateway.sendConfirmation(outgoingMessage);
    }

    private Confirmation constructConfirmation(Message message, List<ValidationResult> validationResults) {
        Confirmation confirmation = new Confirmation("BookStore", message.sender());
        confirmation.setMessageID(UUID.randomUUID().toString());
        confirmation.setCorrelationID(message.messageID());
        confirmation.setSuccessful(validationResults.isEmpty());
        String errorMessage = validationResults.isEmpty() ? null : validationResults.get(0).getErrorMessage();
        confirmation.setErrorMessage(errorMessage);

        return confirmation;
    }

}
