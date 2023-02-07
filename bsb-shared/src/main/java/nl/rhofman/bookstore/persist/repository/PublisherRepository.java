package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

@Dependent
public class PublisherRepository extends AbstractRepository<Publisher> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Publisher Entity");

    @Inject
    protected PublisherRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    public Publisher getReference(@NotNull Long id) {
        return execute(() -> em.getReference(Publisher.class, id));
    }

    public Publisher findByName(@NotNull String name) {
        TypedQuery<Publisher> query = em.createNamedQuery(Publisher.FIND_BY_NAME, Publisher.class);
        query.setParameter("name", name);
        return execute(() -> query.getSingleResult());
    }

}
