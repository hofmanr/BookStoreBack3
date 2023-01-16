package nl.rhofman.exception;

import java.util.Collection;

public class ArgumentValidator {
    private ArgumentValidator() {
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void notEmpty(Collection<T> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void notNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String argument, String message) {
        if (argument == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
