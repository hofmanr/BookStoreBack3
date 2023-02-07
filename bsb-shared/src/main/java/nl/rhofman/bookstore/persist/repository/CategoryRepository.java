package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

@Dependent
public class CategoryRepository extends AbstractRepository<Category> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Category Entity");

    @Inject
    protected CategoryRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    public Category getReference(@NotNull Long id) {
        return execute(() -> em.getReference(Category.class, id));
    }

    public Category findByName(@NotNull String name) {
        TypedQuery<Category> query = em.createNamedQuery(Category.FIND_BY_NAME, Category.class);
        query.setParameter("name", name);
        return execute(() -> query.getSingleResult());
    }

}
