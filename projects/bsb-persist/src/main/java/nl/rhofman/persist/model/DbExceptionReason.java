package nl.rhofman.persist.model;

import nl.rhofman.exception.domain.ExceptionReason;

public class DbExceptionReason {
    public static final ExceptionReason ILLEGAL_STATE = new ExceptionReason("ISE", "Database in in an illegal state");

    public static final ExceptionReason DUPLICATE_KEY = new ExceptionReason("DKY", "Entity with Id already exists");
    public static final ExceptionReason ENTITY_EXISTS = new ExceptionReason("EES", "Entity already exists");
    public static final ExceptionReason LOCK_TIMEOUT = new ExceptionReason("LTT", "Lock timeout");
    public static final ExceptionReason NON_UNIQUE_RESULT = new ExceptionReason("NUR", "Non-unique result, unique result expected");
    public static final ExceptionReason NO_RESULT = new ExceptionReason("NRT", "No results/records found");
    public static final ExceptionReason OPTIMISTIC_LOCK = new ExceptionReason("OLK", "Optimistic lock");
    public static final ExceptionReason PESSIMISTIC_LOCK = new ExceptionReason("PLK", "Pessimistic lock");
    public static final ExceptionReason QUERY_TIMEOUT = new ExceptionReason("QTT", "Timeout while querying for data");
    public static final ExceptionReason CONSTRAINT_VIOLATION = new ExceptionReason("CVN", "Constraint violation");
    public static final ExceptionReason NO_DATA_FOUND = new ExceptionReason("NDF", "No data found");
    public static final ExceptionReason GENERAL_ORM_EXCEPTION = new ExceptionReason("GOE", "Problem with EntityManager accessing the database");

    private DbExceptionReason() {
    }
}
