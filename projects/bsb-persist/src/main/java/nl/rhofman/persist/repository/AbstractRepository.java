package nl.rhofman.persist.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.exception.dao.DataAccessException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.DbFunction;
import nl.rhofman.persist.model.BaseEntity;
import nl.rhofman.persist.model.DbExceptionReason;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Supplier;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

public abstract class AbstractRepository<T extends BaseEntity> {
    public static final String DATA_ORIGIN = "JPA";

    private Class<T> clazz;
    protected EntityManager em;

    @Inject
    private DbExecutor exceptionResolver;

    // Inject a default Entity Manager if there is no constructor in the superclass
    @Inject
    protected AbstractRepository(EntityManager entityManager) {
        this.em = entityManager;

        Type type = getClass().getGenericSuperclass();
        if (type.getTypeName().endsWith("GenericRepository")) {
            return;
        }

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != AbstractRepository.class) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        // definition of the class should be something like: public class BookRepository extends BaseRepository<Book>
        // Argument 0 is in this case Book
        this.clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    protected void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    protected abstract ExceptionOrigin getExceptionOrigin();

    public T create(@NotNull T entity) {
        return execute(() -> {
            em.persist(entity);
            em.flush();
            return entity;
        });
    }

    public T update(@NotNull T entity) {
        return execute(() -> {
            T storedEntity = em.merge(entity);
            em.flush();
            return storedEntity;
        });
    }

    public Collection<T> findAll() {
        return execute(() ->
                em.createQuery("select e from " + clazz.getSimpleName() + " e").getResultList());
    }

    public T find(Long id) {
        return execute(() -> em.find(clazz, id));
    }

    @Transactional(REQUIRED)
    public void remove(@NotNull T entity) {
        if (entity.getId() == null) {
            return;
        }
        remove(entity.getId());
    }

    @Transactional(REQUIRED)
    public void remove(@NotNull Long id) {
//        try {
            execute(() -> {
                T storedEntity = em.getReference(clazz, id);
                em.remove(storedEntity);
                em.flush();
            });
//        } catch (EntityNotFoundException e) {
//            throw new DataAccessException(getExceptionOrigin(), DbExceptionReason.NO_DATA_FOUND, "Could not find " + clazz.getSimpleName() + "  with id: " + id, e);
//        }
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("select count(e) from " + clazz.getSimpleName() + " e", Long.class);
        return execute(query::getSingleResult);
    }


    protected <U> U execute(Supplier<U> supplier) {
        return exceptionResolver.execute(supplier, getExceptionOrigin());
    }

    protected void execute(DbFunction function) {
        exceptionResolver.execute(function, getExceptionOrigin());
    }

}
