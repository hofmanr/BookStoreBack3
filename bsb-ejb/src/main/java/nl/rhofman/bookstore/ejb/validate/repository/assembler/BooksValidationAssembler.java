package nl.rhofman.bookstore.ejb.validate.repository.assembler;

import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Author;
import nl.rhofman.bookstore.ejb.catalogue.domain.Book;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.validate.repository.ValidationAssembler;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationMessage;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;
import nl.rhofman.bookstore.ejb.validate.repository.condition.CategoryExistsValidator;
import nl.rhofman.bookstore.ejb.validate.repository.condition.PublisherExistsValidator;
import nl.rhofman.bookstore.ejb.validate.repository.rule.AuthorNameValidator;
import nl.rhofman.bookstore.ejb.validate.repository.rule.MinimumLengthValidator;
import nl.rhofman.bookstore.persist.service.CategoryService;
import nl.rhofman.bookstore.persist.service.PublisherService;

import java.util.ArrayList;
import java.util.List;

@ValidationMessage(Catalogue.class)
public class BooksValidationAssembler implements ValidationAssembler<Catalogue> {
    private List<Validator> validators = new ArrayList<>();

    @Inject
    private CategoryService categoryService;
    @Inject
    private PublisherService publisherService;

    @Override
    public List<Validator> map(Catalogue catalogue) {
        catalogue.getBooks().stream()
                .map(Book::getCategory)
                .forEach(cat -> validators.add(new CategoryExistsValidator(cat, categoryService)));

        catalogue.getBooks().stream().map(Book::getAuthors).forEach(this::addAuthorValidators);

        catalogue.getBooks().stream()
                .map(Book::getPublisher)
                .forEach(pub -> validators.add(new PublisherExistsValidator(pub, publisherService)));

        return validators;
    }

    private void addAuthorValidators(List<Author> authors) {
        authors.stream().forEach(a -> {
            validators.add(new MinimumLengthValidator("authorName", a.getLastName(), 2));
            validators.add(new AuthorNameValidator(a.getFirstName(), a.getInitials()));
        });
        // Also: check uniqueness of authors per book, etc.
    }

}
