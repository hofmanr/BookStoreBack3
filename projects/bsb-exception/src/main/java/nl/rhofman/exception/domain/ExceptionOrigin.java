package nl.rhofman.exception.domain;

import nl.rhofman.exception.Validator;

public class ExceptionOrigin {
    private final String code;
    private final String origin;

    public ExceptionOrigin(String code, String origin) {
        Validator.notNull(code, "The code should not be null");
        Validator.notNull(origin, "The origin should not be null");
        Validator.notEmpty(code, "The code should not be an empty string");
        Validator.notEmpty(origin, "The origin should not be an empty string");
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
