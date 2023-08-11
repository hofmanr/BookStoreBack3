package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.bookstore.persist.model.Metadata;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.Collection;
import java.util.List;

@Dependent
public class MessageRepository extends AbstractRepository<Message> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Message Entity");

    @Inject
    protected MessageRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    @Override
    public Message update(Message entity) {
        throw new UnsupportedOperationException("Message can not be updated");
    }

    @Override
    public Collection<Message> findAll() {
        throw new UnsupportedOperationException("It is not allowed to get all messages");
    }
}
