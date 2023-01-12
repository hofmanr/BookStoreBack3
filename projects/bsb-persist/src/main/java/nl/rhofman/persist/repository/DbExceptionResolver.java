package nl.rhofman.persist.repository;

import java.util.function.Supplier;

public class DbExceptionResolver {

    public <U> U execute(Supplier<U> supplier) {
        try {
            return supplier.get();
        } catch(IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }
}
