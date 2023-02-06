package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.BookInStock;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

@Dependent
public class BookInStockRepository extends AbstractRepository<BookInStock> {
    public static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Book in Stock");

    @Inject
    public BookInStockRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    public BookInStock findByBook(Book book) {
        TypedQuery<BookInStock> query = em.createNamedQuery(BookInStock.FIND_BY_BOOK, BookInStock.class);
        query.setParameter("book", book);
        return execute(() -> query.getSingleResult());
    }
}
