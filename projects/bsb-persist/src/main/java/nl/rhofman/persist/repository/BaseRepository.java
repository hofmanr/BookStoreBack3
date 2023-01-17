package nl.rhofman.persist.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.DbFunction;
import nl.rhofman.persist.model.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Supplier;

public abstract class BaseRepository<T extends BaseEntity> {
    public static final String DATA_ORIGIN = "JPA";
    @Inject
    protected EntityManager entityManager;
    private Class<T> clazz;
    @Inject
    private DbExecutor exceptionResolver;

    protected BaseRepository() {
        Type type = getClass().getGenericSuperclass();
        if (type.getTypeName().endsWith("GenericRepository")) {
            return;
        }

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != BaseRepository.class) {
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

    public void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract ExceptionOrigin getExceptionOrigin();

    public T create(T entity) {
        return execute(() -> {
            entityManager.persist(entity);
            entityManager.flush();
            return entity;
        });
    }

    public T update(T entity) {
        return execute(() -> {
            T storedEntity = entityManager.merge(entity);
            entityManager.flush();
            return storedEntity;
        });
    }

    public Collection<T> findAll() {
        return execute(() ->
                entityManager.createQuery("select e from " + clazz.getSimpleName() + " e").getResultList());
    }

    public T find(Long id) {
        return execute(() -> entityManager.find(clazz, id));
    }

    public void remove(T entity) {
        if (entity.getId() == null) {
            return;
        }
        remove(entity.getId());
    }

    public void remove(Long id) {
        execute(() -> {
            T storedEntity = entityManager.getReference(clazz, id);
            entityManager.remove(storedEntity);
            entityManager.flush();
        });
    }

    protected <U> U execute(Supplier<U> supplier) {
        return exceptionResolver.execute(supplier, getExceptionOrigin());
    }

    protected void execute(DbFunction function) {
        exceptionResolver.execute(function, getExceptionOrigin());
    }

}
