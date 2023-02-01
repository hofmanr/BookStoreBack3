package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

@Dependent
public class BookRepository extends AbstractRepository<Book> {

    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Book Entity");

    @Inject
    public BookRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    @Transactional(REQUIRED)
    public Book create(@NotNull Book book) {
        final Publisher bookPublisher = book.getPublisher();
        if (bookPublisher != null) {
            Publisher publisher =
                    execute(() -> em.getReference(bookPublisher.getClass(), bookPublisher.getId()));
            book.setPublisher(publisher);
        }
        // TODO check authors...
        return execute(() -> {
            em.persist(book);
            em.flush();
            return book;
        });
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER By b.title DESC", Book.class);
        return execute(() -> query.getResultList());
    }
}
