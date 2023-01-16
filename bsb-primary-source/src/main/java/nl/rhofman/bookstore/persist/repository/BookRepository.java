package nl.rhofman.bookstore.persist.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.bookstore.util.NumberGenerator;
import nl.rhofman.bookstore.util.TextUtil;
import nl.rhofman.bookstore.util.ThirteenDigits;
import nl.rhofman.exception.dao.DataException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.DbExceptionReason;
import nl.rhofman.persist.repository.BaseRepository;
import nl.rhofman.persist.repository.DbExecutor;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Transactional(SUPPORTS)
public class BookRepository extends BaseRepository<Book> {

    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Book Entity");

    @Inject
    private DbExecutor dbExecutor;

    @Inject
    private TextUtil textUtil;

    @Inject
    @ThirteenDigits
    private NumberGenerator generator;

    // For unittest
    protected void setEm(EntityManager em) {
        this.entityManager = em;
    }

    // For unittest
    protected void setTextUtil(TextUtil textUtil) {
        this.textUtil = textUtil;
    }

    @Override
    public ExceptionOrigin getExceptionOrigin() {
        return null;
    }

    public Book find(@NotNull Long id) {
        return dbExecutor.execute(() -> entityManager.find(Book.class, id), EXCEPTION_ORIGIN);
    }

    @Transactional(REQUIRED)
    public Book create(@NotNull Book book) {
        book.setTitle(textUtil.sanitize(book.getTitle()));
        if (book.getIsbn() == null)
            book.setIsbn(generator.generateNumber());
        final Publisher bookPublisher = book.getPublisher();
        if (bookPublisher != null) {
            try {
                Publisher publisher = dbExecutor.execute(
                        () -> entityManager.getReference(bookPublisher.getClass(), bookPublisher.getId()),
                        EXCEPTION_ORIGIN
                );
                book.setPublisher(publisher);
            } catch (EntityNotFoundException e) {
                throw new DataException(EXCEPTION_ORIGIN, DbExceptionReason.NO_DATA_FOUND, "Could not find Publisher with id: " + bookPublisher.getId(), e);
            }
        }
        return dbExecutor.execute(() -> {
            entityManager.persist(book);
            entityManager.flush();
            return book;
        }, EXCEPTION_ORIGIN);
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id) {
        try {
            dbExecutor.execute(() -> {
                entityManager.remove(entityManager.getReference(Book.class, id));
                entityManager.flush();
                return null;
            }, EXCEPTION_ORIGIN);
        } catch (EntityNotFoundException e) {
            throw new DataException(EXCEPTION_ORIGIN, DbExceptionReason.NO_DATA_FOUND, "Could not find Book with id: " + id, e);
        }
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b ORDER By b.title DESC", Book.class);
        return dbExecutor.execute(() -> query.getResultList(), EXCEPTION_ORIGIN);
    }

    public Long countAll() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(b) FROM Book b", Long.class);
        return dbExecutor.execute(() -> query.getSingleResult(), EXCEPTION_ORIGIN);
    }
}
