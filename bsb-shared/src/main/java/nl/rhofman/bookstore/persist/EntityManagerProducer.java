package nl.rhofman.bookstore.persist;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Dependent
public class EntityManagerProducer {

    @Produces
    @BSB
    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager entityManager;
}
