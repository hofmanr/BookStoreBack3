package nl.rhofman.persist.Service;

import jakarta.inject.Inject;
import nl.rhofman.exception.service.DataAccessExceptionResolver;
import nl.rhofman.persist.DbFunction;

import java.util.function.Supplier;

public abstract class AbstractService {

    @Inject
    protected DataAccessExceptionResolver exceptionResolver;

    protected <U> U execute(Supplier<U> supplier) {
        return exceptionResolver.execute(supplier);
    }

    protected <U> U execute(Supplier<U> supplier, String message) {
        return exceptionResolver.execute(supplier, message);
    }

    protected void execute(DbFunction function) {
        exceptionResolver.execute(() -> {
            function.exec();
            return null;
        });
    }

    protected void execute(DbFunction function, String message) {
        exceptionResolver.execute(() -> {
            function.exec();
            return null;
        }, message);
    }

}
