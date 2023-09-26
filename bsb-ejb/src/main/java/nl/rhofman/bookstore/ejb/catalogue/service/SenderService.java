package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.dao.CatalogueGateway;
import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;

import java.util.UUID;

public class SenderService {

    @Inject
    private CatalogueGateway catalogueGateway;

    public void sendConfirmation(String recipient, String requestIdentifier, String errorMessage) {
        Confirmation confirmation = new Confirmation("BookStore", recipient);
        confirmation.setMessageID(UUID.randomUUID().toString());
        confirmation.setCorrelationID(requestIdentifier);
        confirmation.setSuccessful(errorMessage == null);
        confirmation.setErrorMessage(errorMessage);
        catalogueGateway.sendConfirmation(confirmation);
    }
}
