package nl.rhofman.bookstore.web.book.domain;

import jakarta.json.bind.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import nl.rhofman.bookstore.web.book.converter.LanguageAdapter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@JsonbPropertyOrder({"id", "title", "description", "isbn", "authors", "publicationDate",
"language", "nbOfPages", "category", "rank", "unitCost", "publisher", "imageUrl"})
public class BookDTO {
    private Long id;

    @NotNull(message = "ISBN cannot be null")
    @Size(min = 1, max = 50, message = "ISBN must be between 1 and 50 characters long")
    private String isbn;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 200)
    private String title;

    @Size(min = 1, max = 10000)
    protected String description;

    @JsonbDateFormat(value = "yyyy-MM-dd", locale = "Locale.ENGLISH") //shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    @Past(message = "Publication Date should be in the past")
    private LocalDate publicationDate;

    private Integer nbOfPages;

    @JsonbTypeAdapter(LanguageAdapter.class)
    private LanguageDTO language;

    private String publisher;

    private String category;

    @JsonbProperty("authors")
    private Set<AuthorDTO> authors = new HashSet<>();

    @Min(value = 1, message = "The minimum value")
    private Float unitCost;

    private Integer rank;

    private String imageUrl;

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
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

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publicationDate=" + publicationDate +
                ", authors=" + authors +
                '}';
    }
}
