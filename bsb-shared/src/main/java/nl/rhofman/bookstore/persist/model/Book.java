package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.*;

@Entity
@Table(name = "Books")
@NamedQueries({
        @NamedQuery(name = Book.FIND_TOP_RATED, query = "SELECT b FROM Book b WHERE b.id in :ids"),
        @NamedQuery(name = Book.FIND_BY_ISBN, query = "SELECT b FROM Book b WHERE b.isbn in :isbn"),
        @NamedQuery(name = Book.SEARCH, query = "SELECT b FROM Book b WHERE UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword ORDER BY b.title")
})
public class Book extends BaseEntityVersion {
    private static final long serialVersionUID = 7492632083922534l;

    public static final String FIND_TOP_RATED = "Book.findTopRated";
    public static final String FIND_BY_ISBN = "Book.findByIsbn";
    public static final String SEARCH = "Book.search";

    @Column(name = "title", length = 200, nullable = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String title;

    @Column(name = "description", length = 10000)
    @Size(min = 1, max = 10000)
    private String description;

    @Column(name = "unit_cost")
    @Min(1)
    private Float unitCost;

    @Column(name = "book_rank") // rank is a preserved word in MySQL
    private Integer rank;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "isbn", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String isbn;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    @Past
    private Date publicationDate;

    @Column(name = "nr_of_pages")
    private Integer nbOfPages;

    @Column(name = "language")
    // use LanguageConverter and not @Enumerated
    private Language language;

    @ManyToOne
    private Publisher publisher;

    @ManyToOne
    private Category category;

    // The Book entity owns the association (only one side can own a relationship).
    // Changes to the database are propagated to the database from this side.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "BooksWritenByAuthors",
        joinColumns = @JoinColumn(name="book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    // ======================================
    // =            Constructors            =
    // ======================================

    public Book() {
    }

    public Book(String isbn, String title, String description, Float unitCost, Integer nbOfPages, Language language, String imageUrl) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.nbOfPages = nbOfPages;
        this.language = language;
        this.imageUrl = imageUrl;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(final Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(final List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getBooks().remove(this);
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return title.equals(book.title) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, isbn);
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", version: " + version;
        if (title != null && !title.trim().isEmpty())
            result += ", title: " + title;
        if (unitCost != null)
            result += ", unitCost: " + unitCost;
        if (isbn != null && !isbn.trim().isEmpty())
            result += ", isbn: " + isbn;
        if (nbOfPages != null)
            result += ", nbOfPage: " + nbOfPages;
        if (publicationDate != null)
            result += ", publicationDate: " + publicationDate;
        if (language != null)
            result += ", language: " + language;
        return result;
    }
}
