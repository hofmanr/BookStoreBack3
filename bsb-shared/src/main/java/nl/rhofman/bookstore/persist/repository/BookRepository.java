package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.List;

@Dependent
public class BookRepository extends AbstractRepository<Book> {

    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Book Entity");

    @Inject
    public BookRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER By b.title DESC", Book.class);
        return execute(() -> query.getResultList());
    }

    public List<Book> findByKeyword(String keyword) {
        TypedQuery<Book> query = em.createNamedQuery(Book.SEARCH, Book.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return execute(() -> query.getResultList());
    }

    public Book findByIsbn(@NotNull String isbn) {
        TypedQuery<Book> query = em.createNamedQuery(Book.FIND_BY_ISBN, Book.class);
        query.setParameter("isbn", isbn);
        return execute(() -> query.getSingleResult());
    }

    public Book getReference(@NotNull Long id) {
        return execute(() -> em.getReference(Book.class, id));
    }
}
