package nl.rhofman.exception.dao;

import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;

public class DataException extends AbstractException {

    public DataException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message) {
        super(exceptionOrigin, exceptionReason, message);
    }

    public DataException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message, Throwable cause) {
        super(exceptionOrigin, exceptionReason, message, cause);
    }

    public DataException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, Throwable cause) {
        super(exceptionOrigin, exceptionReason, cause);
    }
}
