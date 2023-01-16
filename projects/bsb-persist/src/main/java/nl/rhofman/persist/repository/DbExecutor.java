package nl.rhofman.persist.repository;

import jakarta.validation.constraints.NotNull;
import nl.rhofman.exception.dao.DataException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.DbExceptionReason;

import java.util.function.Supplier;

public class DbExecutor {

    public <U> U execute(Supplier<U> supplier, @NotNull(message = "{bookstore.validation.NotNull.origin}") ExceptionOrigin exceptionOrigin) {
        try {
            return supplier == null ? null : supplier.get();
        } catch(IllegalStateException e) {
            throw new DataException(exceptionOrigin, DbExceptionReason.ILLEGAL_STATE, e);
        }
    }
}
