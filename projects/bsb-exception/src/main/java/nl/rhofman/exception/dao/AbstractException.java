package nl.rhofman.exception.dao;

import nl.rhofman.exception.Validator;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;

import java.time.LocalDateTime;

public abstract class AbstractException extends RuntimeException {
    private final LocalDateTime creationDate;
    private final ExceptionOrigin exceptionOrigin;
    private final ExceptionReason exceptionReason;

    public AbstractException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message) {
        super(message);
        this.creationDate = LocalDateTime.now();
        Validator.notNull(exceptionOrigin, "The Exception Origin should not be null");
        Validator.notNull(exceptionReason, "The Exception Reason should not be null");
        this.exceptionOrigin = exceptionOrigin;
        this.exceptionReason = exceptionReason;
    }

    public AbstractException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, Throwable cause) {
        super(cause);
        this.creationDate = LocalDateTime.now();
        Validator.notNull(exceptionOrigin, "The Exception Origin should not be null");
        Validator.notNull(exceptionReason, "The Exception Reason should not be null");
        this.exceptionOrigin = exceptionOrigin;
        this.exceptionReason = exceptionReason;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public ExceptionReason getExceptionReason() {
        return exceptionReason;
    }
}
