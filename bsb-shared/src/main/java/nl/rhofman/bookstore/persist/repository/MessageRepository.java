package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

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
    public Message create(Message entity) {
        throw new UnsupportedOperationException("This is not the way to create a message; use an other method");
    }

    @Override
    public Message update(Message entity) {
        throw new UnsupportedOperationException("Message can not be updated");
    }

    @Override
    public void remove(Message entity) {
        throw new UnsupportedOperationException("It is not allowed to remove messages");
    }

    @Override
    public void remove(Long id) {
        throw new UnsupportedOperationException("It is not allowed to remove messages");
    }

    public Message saveMessage(String Message) {
        Message message = new MessageBuilder(Message).build();
        return execute(() -> {
            em.persist(message);
            em.flush();
            return message;
        });
    }
}
