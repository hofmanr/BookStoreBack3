package nl.rhofman.bookstore.ejb.catalogue.gateway;

import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;

public interface CatalogueGateway {

    void sendConfirmation(Confirmation confirmation);
}