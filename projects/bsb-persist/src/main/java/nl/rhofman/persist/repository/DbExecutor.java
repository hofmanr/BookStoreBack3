package nl.rhofman.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.exception.dao.DataAccessException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.model.DbExceptionReason;
import nl.rhofman.persist.DbFunction;

import java.util.function.Supplier;

@Dependent
public class DbExecutor {

    public <U> U execute(Supplier<U> supplier, @NotNull(message = "{bookstore.validation.NotNull.origin}") ExceptionOrigin exceptionOrigin) {
        try {
            return supplier == null ? null : supplier.get();
        } catch(IllegalStateException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.ILLEGAL_STATE, e.getMessage(), e);
        } catch(LockTimeoutException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.LOCK_TIMEOUT, e.getMessage(), e);
        } catch(EntityExistsException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.ENTITY_EXISTS, e.getMessage(), e);
        } catch(EntityNotFoundException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.NO_DATA_FOUND, e.getMessage(), e);
        } catch(PessimisticLockException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.PESSIMISTIC_LOCK, e.getMessage(), e);
        } catch(OptimisticLockException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.OPTIMISTIC_LOCK, e.getMessage(), e);
        } catch(NoResultException e) {
            return null; // getSingleResult doesn't have a result
        } catch(NonUniqueResultException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.NON_UNIQUE_RESULT, e.getMessage(), e);
        } catch(ConstraintViolationException e) {
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.CONSTRAINT_VIOLATION, e.getMessage(), e);
        } catch(PersistenceException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new DataAccessException(exceptionOrigin, DbExceptionReason.DUPLICATE_KEY, e.getMessage(), e);
            }
            throw new DataAccessException(exceptionOrigin, DbExceptionReason.GENERAL_ORM_EXCEPTION, e.getMessage(), e);
        }
    }

    public void execute(DbFunction function, @NotNull(message = "{bookstore.validation.NotNull.origin}") ExceptionOrigin exceptionOrigin) {
        execute(() -> {
            function.exec();
            return null;
        }, exceptionOrigin);
    }
}
