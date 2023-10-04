package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.CatalogueGateway;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageProcessed;
import nl.rhofman.bookstore.ejb.validate.domain.Invalid;
import nl.rhofman.bookstore.ejb.validate.domain.Valid;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Dependent
public class CatalogueReplyService {

    @Inject
    private CatalogueGateway catalogueGateway;

    public void processInvalidMessageProcessedEvent(@Observes @Invalid @DomainType(Catalogue.class) MessageProcessed messageProcessed) {
        Message message = messageProcessed.getMessage();
        List<ValidationResult> validationResults = messageProcessed.getValidationResults();
// TODO        catalogueGateway.sendConfirmation(constructConfirmation(message, validationResults));
    }

    public void processValidMessageProcessedEvent(@Observes @Valid @DomainType(Catalogue.class) MessageProcessed messageProcessed) {
        Message message = messageProcessed.getMessage();
// TODO        catalogueGateway.sendConfirmation(constructConfirmation(message));
    }

    private Confirmation constructConfirmation(Message message) {
        return constructConfirmation(message, Collections.emptyList());
    }

    private Confirmation constructConfirmation(Message message, List<ValidationResult> validationResults) {
        Header header = message.getHeader();
        Confirmation confirmation = new Confirmation("BookStore", header.getMessageSender());
        confirmation.setMessageID(UUID.randomUUID().toString());
        confirmation.setCorrelationID(header.getMessageID());
        confirmation.setSuccessful(validationResults.isEmpty());
        String errorMessage = validationResults.isEmpty() ? null : validationResults.get(0).getErrorMessage();
        confirmation.setErrorMessage(errorMessage);

        return confirmation;
    }

}
