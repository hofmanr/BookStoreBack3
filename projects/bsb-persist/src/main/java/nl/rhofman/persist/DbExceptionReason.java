package nl.rhofman.persist;

import nl.rhofman.exception.domain.ExceptionReason;

public class DbExceptionReason {
    public static final ExceptionReason ILLEGAL_STATE = new ExceptionReason("ISE", "Database in in an illegal state");
    private DbExceptionReason() {
    }
}
