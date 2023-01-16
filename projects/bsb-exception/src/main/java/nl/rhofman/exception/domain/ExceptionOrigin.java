package nl.rhofman.exception.domain;

import nl.rhofman.exception.ArgumentValidator;

public class ExceptionOrigin {
    private final String code;
    private final String origin;

    public ExceptionOrigin(String code, String origin) {
        ArgumentValidator.notNull(code, "The code should not be null");
        ArgumentValidator.notNull(origin, "The origin should not be null");
        ArgumentValidator.notEmpty(code, "The code should not be an empty string");
        ArgumentValidator.notEmpty(origin, "The origin should not be an empty string");
        this.code = code;
        this.origin = origin;
    }

    public String getCode() {
        return code;
    }

    public String getOrigin() {
        return origin;
    }
}
