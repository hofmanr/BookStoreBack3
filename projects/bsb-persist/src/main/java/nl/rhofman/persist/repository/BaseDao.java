package nl.rhofman.persist.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import nl.rhofman.exception.domain.ExceptionOrigin;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseDao<T> {

    private Class<T> clazz;

    @Inject
    protected EntityManager entityManager;

    @Inject
    private DbExceptionResolver exceptionResolver;

    protected BaseDao() {
        Type type = getClass().getGenericSuperclass();
        if(type.getTypeName().endsWith("GenericDaoImpl")) {
            return;
        }

        while(!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != BaseDao.class) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }
    }

    public void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract ExceptionOrigin getExceptionOrigin();

    public T create(T entity) {
        return exceptionResolver.execute(() -> {
            entityManager.persist(entity);
            entityManager.flush();
            return entity;
        }, getExceptionOrigin());
    }

}
