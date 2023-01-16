package nl.rhofman.bookstore.persist;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Produces;

public class Configuration {

    @Produces
    @PersistenceContext(name = "bookStorePU")
    public EntityManager produceEntityManager(EntityManager em) {
        return em;
    }
}
