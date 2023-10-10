package nl.rhofman.bookstore.ejb.catalogue.domain;

import nl.rhofman.bookstore.ejb.message.domain.DomainObject;

import java.util.ArrayList;
import java.util.List;

public class Catalogue extends DomainObject {
    private List<Book> books = new ArrayList<Book>();

    public Catalogue() {
        super();
    }

    public Catalogue(String sender, String recipient) {
        super(sender, recipient);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
