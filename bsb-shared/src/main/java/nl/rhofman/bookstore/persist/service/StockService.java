package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.BookInStock;
import nl.rhofman.bookstore.persist.repository.BookInStockRepository;
import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.persist.Service.AbstractService;
import nl.rhofman.persist.model.DbExceptionReason;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class StockService extends AbstractService {

    @Inject
    private BookInStockRepository bookInStockRepository;

    @Inject
    private BookService bookService;

    /**
     * Update quantity in stock if there is already a record.
     * If there is no record for the book, create a new one.
     * @param book
     * @param quantity
     * @return
     */
    @Transactional(REQUIRED)
    public BookInStock updateQuantityInStock(@NotNull Book book, @NotNull Long quantity) {
        // The Book ID is mandatory
        if (book.getId() == null) {
            throw new ServiceException(bookInStockRepository.EXCEPTION_ORIGIN, DbExceptionReason.NO_DATA_FOUND, "Book not found");
        }

        final Book bookRef = bookService.getReference(book.getId());
        if (bookRef == null) {
            throw new ServiceException(bookInStockRepository.EXCEPTION_ORIGIN, DbExceptionReason.NO_DATA_FOUND, "Book not found");
        }

        // see if a record already exists
        BookInStock bookInStock = bookInStockRepository.findByBook(book);
        if (bookInStock == null) {
            final BookInStock newBookInStock = new BookInStock(bookRef, quantity);
            return execute(() -> bookInStockRepository.create(newBookInStock), "Error creating a new Book In Stock in StockService");
        }
        // Update the quantity
        bookInStock.setQuantity(bookInStock.getQuantity() + quantity);
        return execute(() -> bookInStockRepository.update(bookInStock), "Error updating a Book In Stock in StockService");
    }

    public BookInStock findByBook(Book book) {
        return bookInStockRepository.findByBook(book);
    }
}
