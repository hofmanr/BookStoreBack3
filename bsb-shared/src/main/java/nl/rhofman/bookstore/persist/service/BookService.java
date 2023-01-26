package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.repository.BookRepository;
import nl.rhofman.bookstore.util.NumberGenerator;
import nl.rhofman.bookstore.util.TextUtil;
import nl.rhofman.bookstore.util.ThirteenDigits;
import nl.rhofman.persist.Service.AbstractService;

import java.util.List;

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
        return execute(() -> bookRepository.create(book), "Error creating a new Book in BookService");
    }

    public Book get(Long id) {
        return execute(() -> bookRepository.find(id), "Error searching a Book in BookService");
    }

    public List<Book> getAll() {
        return execute(() -> bookRepository.findAll(), "Error searching Books in BookService");
    }

    public Long countAll() {
        return execute(() -> bookRepository.countAll(), "Error counting Books in BookService");
    }

    @Transactional(REQUIRED)
    public void delete(Long id) {
        execute(() -> bookRepository.remove(id), "Error deleting a Book in BookService");
    }
}
