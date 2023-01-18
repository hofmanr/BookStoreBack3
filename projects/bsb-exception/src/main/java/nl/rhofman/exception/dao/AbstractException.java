package nl.rhofman.exception.dao;

import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractException extends RuntimeException {
    private final LocalDateTime creationDate;
    private final ExceptionOrigin exceptionOrigin;
    private final ExceptionReason exceptionReason;

    protected AbstractException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message) {
        super(message);
        checkArguments(exceptionOrigin, exceptionReason);
        this.creationDate = LocalDateTime.now();
        this.exceptionOrigin = exceptionOrigin;
        this.exceptionReason = exceptionReason;
    }

    protected AbstractException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message, Throwable cause) {
        super(message, cause);
        checkArguments(exceptionOrigin, exceptionReason);
        this.creationDate = LocalDateTime.now();
        this.exceptionOrigin = exceptionOrigin;
        this.exceptionReason = exceptionReason;
    }

    protected AbstractException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, Throwable cause) {
        super(cause);
        checkArguments(exceptionOrigin, exceptionReason);
        this.creationDate = LocalDateTime.now();
        this.exceptionOrigin = exceptionOrigin;
        this.exceptionReason = exceptionReason;
    }

    private void checkArguments(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason) {
        Objects.requireNonNull(exceptionOrigin, "The Exception Origin should not be null");
        Objects.requireNonNull(exceptionReason, "The Exception Reason should not be null");
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public ExceptionOrigin getOrigin() {
        return exceptionOrigin;
    }

    public ExceptionReason getReason() {
        return exceptionReason;
    }
}
