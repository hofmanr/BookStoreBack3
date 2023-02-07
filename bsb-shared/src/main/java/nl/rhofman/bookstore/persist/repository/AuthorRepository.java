package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Author;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.Date;
import java.util.List;

@Dependent
public class AuthorRepository extends AbstractRepository<Author> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Author Entity");

    @Inject
    protected AuthorRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    public Author getReference(@NotNull Long id) {
        return execute(() -> em.getReference(Author.class, id));
    }

    public List<Author> findByLastName(String lastName) {
        TypedQuery<Author> query = em.createNamedQuery(Author.FIND_BY_LASTNAME, Author.class)
                .setParameter("lastName", lastName);
        return execute(() -> query.getResultList());
    }


}
