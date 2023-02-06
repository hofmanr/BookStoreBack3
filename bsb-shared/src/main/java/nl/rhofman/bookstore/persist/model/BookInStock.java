package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.Objects;

@Entity
@Table(name = "BooksInStock")
@NamedQueries(
        @NamedQuery(name = BookInStock.FIND_BY_BOOK, query = "SELECT s FROM BookInStock s WHERE Book = :book")
)
public class BookInStock extends BaseEntityVersion {
    private static final long serialVersionUID = 4352637393922321l;

    public static final String FIND_BY_BOOK = "BookInStock.findByBook";

    @Column(name = "quantity")
    @Min(0)
    private Long quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true, nullable = false)
    private Book book;

    public BookInStock() {
    }

    public BookInStock(Book book, Long quantity) {
        this.quantity = quantity;
        this.book = book;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookInStock)) return false;
        if (!super.equals(o)) return false;
        BookInStock that = (BookInStock) o;
        return quantity.equals(that.quantity) && book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantity, book);
    }

    @Override
    public String toString() {
        return "BookInStock{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", book=" + book +
                '}';
    }
}
