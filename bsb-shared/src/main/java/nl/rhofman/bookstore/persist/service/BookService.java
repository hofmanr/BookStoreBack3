package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.model.Author;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.bookstore.persist.repository.BookRepository;
import nl.rhofman.bookstore.util.NumberGenerator;
import nl.rhofman.bookstore.util.TextUtil;
import nl.rhofman.bookstore.util.ThirteenDigits;
import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.persist.Service.AbstractService;
import nl.rhofman.persist.model.DbExceptionReason;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class BookService extends AbstractService {
    @Inject
    private BookRepository bookRepository;

    @Inject
    private AuthorService authorService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private PublisherService publisherService;

    @Inject
    private TextUtil textUtil;

    @Inject
    @ThirteenDigits
    private NumberGenerator generator;

    /**
     * Automatically adds Publishers, Categories and Authors.
     * @param book
     * @return
     */
    @Transactional(REQUIRED)
    public Book create(Book book) {
        book.setTitle(textUtil.sanitize(book.getTitle()));
        if (book.getIsbn() == null) {
            book.setIsbn(generator.generateNumber());
        }

        // Get the publisher from the database (by ID or Name)
        final Publisher bookPublisher = book.getPublisher();
        book.setPublisher(getDbPublisher(bookPublisher));

        // Get the category from the database (by ID or Name)
        final Category bookCategory = book.getCategory();
        book.setCategory(getDbCategory(bookCategory));

        // Check the authors...
        final List<Author> authors = book.getAuthors();
        book.setAuthors(getDbAuthors(authors));

        return execute(() -> bookRepository.create(book), "Error creating a new Book in BookService");
    }

    private Publisher getDbPublisher(Publisher publisher) {
        if (publisher == null || (publisher.getId() == null && publisher.getName() == null)) {
            return null;
        }

        Publisher dbPublisher;
        dbPublisher = publisher.getId() != null ?
                publisherService.getReference(publisher.getId()) :
                publisherService.findByName(publisher.getName());

        // Add the publisher if he doesn't exist
        if (dbPublisher == null && publisher.getId() == null) {
            dbPublisher = publisherService.create(publisher);
        }
        if (dbPublisher == null) {
            throw new ServiceException(bookRepository.getExceptionOrigin(), DbExceptionReason.NO_DATA_FOUND, "Publisher not found");
        }
        return dbPublisher;
    }

    private Category getDbCategory(Category category) {
        if (category == null || (category.getId() == null && category.getName() == null)) {
            return null;
        }

        Category dbCategory;
        dbCategory = category.getId() != null ?
                categoryService.getReference(category.getId()) :
                categoryService.findByName(category.getName());

        // Add the category if it doesn't exist
        if (dbCategory == null && category.getId() == null) {
            dbCategory = categoryService.create(category);
        }
        if (dbCategory == null) {
            throw new ServiceException(bookRepository.getExceptionOrigin(), DbExceptionReason.NO_DATA_FOUND, "Category not found");
        }
        return dbCategory;
    }

    private List<Author> getDbAuthors(List<Author> authors) {
        UnaryOperator<Author> findAuthor = author -> {
            Author dbAuthor = null;
          if (author.getId() != null) {
              dbAuthor = authorService.getReference(author.getId());
          }
          if (dbAuthor == null && author.getLastName() != null) {
              dbAuthor = authorService.search(author.getLastName(), author.getFirstName(), author.getDateOfBirth());
          }

          // Add a new author if the author doesn't exist
          if (dbAuthor == null && author.getId() == null) {
              dbAuthor = authorService.create(author);
          }
          if (dbAuthor == null) {
              throw new ServiceException(bookRepository.getExceptionOrigin(), DbExceptionReason.NO_DATA_FOUND, "Author not found");
          }
          return dbAuthor;
        };

        return authors.stream()
                .map(findAuthor)
                .collect(Collectors.toList());
    }

    public Book getReference(@NotNull Long id) {
        return execute(() -> bookRepository.getReference(id), "Error getting Book by ID");
    }

    public Book find(Long id) {
        return execute(() -> bookRepository.find(id), "Error searching a Book in BookService");
    }

    public List<Book> findAll() {
        return execute(() -> bookRepository.findAll(), "Error searching Books in BookService");
    }

    public Book findByIsbn(@NotNull String isbn) {
        return execute(() -> bookRepository.findByIsbn(isbn), "Error searching a Book for the given ISBN");
    }

    /**
     * Keyword must exist in Title or Description
     * @param keyword
     * @return
     */
    public List<Book> search(@NotNull String keyword) {
        return execute(() -> bookRepository.findByKeyword(keyword));
    }

    public Long countAll() {
        return execute(() -> bookRepository.countAll(), "Error counting Books in BookService");
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id) {
        execute(() -> bookRepository.remove(id), "Error deleting a Book in BookService");
    }
}
