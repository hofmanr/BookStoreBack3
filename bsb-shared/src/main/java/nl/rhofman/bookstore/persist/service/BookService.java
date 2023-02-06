package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.persist.model.Author;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.bookstore.persist.repository.BookRepository;
import nl.rhofman.bookstore.util.NumberGenerator;
import nl.rhofman.bookstore.util.TextUtil;
import nl.rhofman.bookstore.util.ThirteenDigits;
import nl.rhofman.persist.Service.AbstractService;

import java.util.List;
import java.util.stream.Collectors;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class BookService extends AbstractService {
    @Inject
    private BookRepository bookRepository;

    @Inject
    private TextUtil textUtil;

    @Inject
    @ThirteenDigits
    private NumberGenerator generator;

    @Transactional(REQUIRED)
    public Book create(Book book) {
        book.setTitle(textUtil.sanitize(book.getTitle()));
        if (book.getIsbn() == null) {
            book.setIsbn(generator.generateNumber());
        }

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


        return execute(() -> bookRepository.create(book), "Error creating a new Book in BookService");
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


    public Book get(Long id) {
        return execute(() -> bookRepository.find(id), "Error searching a Book in BookService");
    }

    public List<Book> getAll() {
        return execute(() -> bookRepository.findAll(), "Error searching Books in BookService");
    }

    /**
     * Keyword must exist in Title or Description
     * @param keyword
     * @return
     */
    public List<Book> search(String keyword) {
        return execute(() -> bookRepository.findByKeyword(keyword));
    }

    public Long countAll() {
        return execute(() -> bookRepository.countAll(), "Error counting Books in BookService");
    }

    @Transactional(REQUIRED)
    public void delete(Long id) {
        execute(() -> bookRepository.remove(id), "Error deleting a Book in BookService");
    }
}
