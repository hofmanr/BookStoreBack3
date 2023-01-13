package nl.rhofman.persist.repository;

import nl.rhofman.exception.Validator;
import nl.rhofman.exception.dao.DataException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.DbExceptionReason;

import java.util.function.Supplier;

public class DbExceptionResolver {
//    private static final ExceptionOrigin ORIGIN_JPA = new ExceptionOrigin("JPA", "Interaction with JPA layer");

    public <U> U execute(Supplier<U> supplier, ExceptionOrigin exceptionOrigin) {
        Validator.notNull(exceptionOrigin, "The Origin of the Exception should not be null");
        try {
            return supplier.get();
        } catch(IllegalStateException e) {
            throw new DataException(exceptionOrigin, DbExceptionReason.ILLEGAL_STATE, e);
        }
    }
}
