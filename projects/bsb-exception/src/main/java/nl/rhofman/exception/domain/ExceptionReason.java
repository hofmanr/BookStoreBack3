package nl.rhofman.exception.domain;

import nl.rhofman.exception.ArgumentValidator;

public class ExceptionReason {
    private final String code;
    private final String reason;

    public ExceptionReason(String code, String reason) {
        ArgumentValidator.notNull(code, "The code should not be null");
        ArgumentValidator.notNull(reason, "The reason should not be null");
        ArgumentValidator.notEmpty(code, "The code should not be an empty string");
        ArgumentValidator.notEmpty(reason, "The code should not be an empty string");
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
