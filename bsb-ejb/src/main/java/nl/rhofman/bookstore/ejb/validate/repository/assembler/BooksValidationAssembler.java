package nl.rhofman.bookstore.ejb.validate.repository.assembler;

import nl.rhofman.bookstore.ejb.catalogue.domain.Author;
import nl.rhofman.bookstore.ejb.catalogue.domain.Book;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationAssembler;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationMessage;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;
import nl.rhofman.bookstore.ejb.validate.repository.rule.AuthorNameValidator;
import nl.rhofman.bookstore.ejb.validate.repository.rule.MinimumLengthValidator;

import java.util.ArrayList;
import java.util.List;

@ValidationMessage(Catalogue.class)
public class BooksValidationAssembler implements ValidationAssembler<Catalogue> {
    private List<Validator> validators = new ArrayList<>();

    @Override
    public List<Validator> map(Catalogue catalogue) {
        catalogue.getBooks().stream().map(Book::getAuthors).forEach(this::addAuthorValidators);
//        catalogue.getBooks().stream().map(Book::getPublisher).forEach(this::addPublisherValidators);
        return validators;
    }

    private void addAuthorValidators(List<Author> authors) {
        authors.stream().forEach(a -> {
            validators.add(new MinimumLengthValidator("authorname", a.getLastName(), 2));
            validators.add(new AuthorNameValidator(a.getFirstName(), a.getInitials()));
            // Todo check uniqueness of authors per book
        });
    }

//    private void addPublisherValidators(Publisher publisher) {
//        validators.add(new PublisherNameValidator(publisher.getName()));
//    }

}
