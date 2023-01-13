package nl.rhofman.exception.domain;

import nl.rhofman.exception.Validator;

public class ExceptionReason {
    private final String code;
    private final String reason;

    public ExceptionReason(String code, String reason) {
        Validator.notNull(code, "The code should not be null");
        Validator.notNull(reason, "The reason should not be null");
        Validator.notEmpty(code, "The code should not be an empty string");
        Validator.notEmpty(reason, "The code should not be an empty string");
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
