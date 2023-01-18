package nl.rhofman.exception.service;

import nl.rhofman.exception.dao.DataAccessException;
import nl.rhofman.exception.dao.ServiceException;

import java.util.function.Supplier;

public class DataAccessExceptionResolver {
    private static final String DEFAULT_MESSAGE = "Service encountered a problem while interacting with the Data Layer";

    public <T> T execute(Supplier<T> supplier) {
        return execute(supplier, DEFAULT_MESSAGE);
    }

    public <T> T execute(Supplier<T> supplier, String message) {
        if (message == null || message.isEmpty()) {
            message = DEFAULT_MESSAGE;
        }
        try {
            return supplier.get();
        } catch (DataAccessException e) {
            throw new ServiceException(e.getOrigin(), e.getReason(), message, e);
        }
    }
}
