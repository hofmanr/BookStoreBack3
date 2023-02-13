package nl.rhofman.bookstore.ejb.jms;

import nl.rhofman.exception.domain.ExceptionReason;

public class JmsExceptionReason {
    public static final ExceptionReason UNSUPPORTED_MESSAGE_TYPE = new ExceptionReason("UMT", "Unsupported message type");

    public static final ExceptionReason GENERAL_EXCEPTION = new ExceptionReason("GEX", "Error retrieving message");
}
