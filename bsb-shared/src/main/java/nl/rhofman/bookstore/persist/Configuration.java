package nl.rhofman.bookstore.persist;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class Configuration {

    @Produces
    @BSB
    @PersistenceContext(name = "bookStorePU")
    private EntityManager entityManager;
}
