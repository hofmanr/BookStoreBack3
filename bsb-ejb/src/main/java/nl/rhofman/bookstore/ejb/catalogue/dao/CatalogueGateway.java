package nl.rhofman.bookstore.ejb.catalogue.dao;

import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;

public interface CatalogueGateway {

    void sendConfirmation(Confirmation confirmation);
}
