package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Metadata;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.List;

@Dependent
public class MetadataRepository extends AbstractRepository<Metadata> {

    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Metadata Entity");

    @Inject
    public MetadataRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    public Metadata findByIdentification(@NotNull String identification) {
        TypedQuery<Metadata> query = em.createNamedQuery(Metadata.FIND_BY_IDENTIFICATION, Metadata.class);
        query.setParameter("identification", identification);
        return execute(() -> query.getSingleResult());
    }

    public List<Metadata> findByMessageType(@NotNull String messageType) {
        TypedQuery<Metadata> query = em.createNamedQuery(Metadata.FIND_BY_MESSAGE_TYPE, Metadata.class);
        query.setParameter("identification", messageType);
        return execute(() -> query.getResultList());
    }

}
