package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.model.Author;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.repository.AuthorRepository;
import nl.rhofman.persist.Service.AbstractService;

import java.util.Date;
import java.util.List;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class AuthorService extends AbstractService {

    @Inject
    private AuthorRepository authorRepository;

    public Author getReference(@NotNull Long id) {
        return execute(() -> authorRepository.getReference(id), "Error getting Author by ID");
    }

    public Author create(Author author) {
        return execute(() -> authorRepository.create(author), "Error creating new Author");
    }

    public Author search(@NotNull String lastName, String firstName, Date datOfBirth) {
        List<Author> authors = execute(() -> authorRepository.findByLastName(lastName), "Error searching Authors by LastName");
        if (authors.isEmpty()) {
            return null;
        }
        return authors.stream()
                .filter(a -> firstName == null || firstName.equalsIgnoreCase(a.getFirstName()))
                .filter(a -> datOfBirth == null || datOfBirth.equals(a.getDateOfBirth()))
                .findFirst().orElse(null);
    }
}
