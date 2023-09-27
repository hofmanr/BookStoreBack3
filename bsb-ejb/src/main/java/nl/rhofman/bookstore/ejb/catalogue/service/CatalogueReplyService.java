package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.event.MessageProcessed;
import nl.rhofman.bookstore.ejb.validate.domain.Invalid;
import nl.rhofman.bookstore.ejb.validate.domain.Valid;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.util.List;
import java.util.UUID;

@Dependent
public class CatalogueReplyService {

    @Inject
    private CatalogueGateway catalogueGateway;

    public void processInvalidMessageProcessedEvent(@Observes @Invalid @DomainType(Catalogue.class) MessageProcessed messageProcessed) {
        Header header = messageProcessed.getHeader();
        List<ValidationResult> validationResults = messageProcessed.getValidationResults();
        String errorMessage = validationResults.isEmpty() ? "Unknown error" : validationResults.get(0).getErrorMessage();
        sendConfirmation(header.getMessageSender(), header.getMessageID(), errorMessage);
    }

    public void processValidMessageProcessedEvent(@Observes @Valid @DomainType(Catalogue.class) MessageProcessed messageProcessed) {
        Header header = messageProcessed.getHeader();
        sendConfirmation(header.getMessageSender(), header.getMessageID(), null);
    }


    public void sendConfirmation(String recipient, String requestIdentifier, String errorMessage) {
        Confirmation confirmation = new Confirmation("BookStore", recipient);
        confirmation.setMessageID(UUID.randomUUID().toString());
        confirmation.setCorrelationID(requestIdentifier);
        confirmation.setSuccessful(errorMessage == null);
        confirmation.setErrorMessage(errorMessage);
        catalogueGateway.sendConfirmation(confirmation);
    }
}
