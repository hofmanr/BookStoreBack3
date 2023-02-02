package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Author;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        // Get the publisher by ID or Name
        final Publisher bookPublisher = book.getPublisher();
        book.setPublisher(getPublisher(bookPublisher));

        // Get the category by ID or Name
        final Category bookCategory = book.getCategory();
        book.setCategory(getCategory(bookCategory));

        // Check authors...
        List<Author> authors = book.getAuthors().stream()
                .map(a -> execute(() -> em.getReference(a.getClass(), a.getId())))
                .collect(Collectors.toList());
        book.setAuthors(authors);

        return execute(() -> {
            em.persist(book);
            em.flush();
            return book;
        });
    }

    private Publisher getPublisher(Publisher publisher) {
        if (publisher == null && publisher.getId() == null && publisher.getName() == null) {
            return null;
        }
        if (publisher.getId() != null) {
            return execute(() -> em.getReference(publisher.getClass(), publisher.getId()));
        }
        TypedQuery<Publisher> query = em.createNamedQuery(Publisher.FIND_BY_NAME, Publisher.class);
        query.setParameter("name", publisher.getName());
        // TODO catch error when no results
        return execute(() -> query.getSingleResult());
    }

    private Category getCategory(Category category) {
        if (category == null && category.getId() == null && category.getName() == null) {
            return null;
        }
        if (category.getId() != null) {
            return execute(() -> em.getReference(category.getClass(), category.getId()));
        }
        TypedQuery<Category> query = em.createNamedQuery(Category.FIND_BY_NAME, Category.class);
        query.setParameter("name", category.getName());
        // TODO catch error when no results
        return execute(() -> query.getSingleResult());
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER By b.title DESC", Book.class);
        return execute(() -> query.getResultList());
    }
}
