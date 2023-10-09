package nl.rhofman.bookstore.ejb.catalogue.gateway;

import nl.rhofman.bookstore.ejb.message.domain.Message;

public interface CatalogueGateway {

    void sendConfirmation(Message message);
}
