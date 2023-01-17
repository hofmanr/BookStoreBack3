package nl.rhofman.exception.domain;

import java.io.Serializable;
import java.util.Objects;

public class ExceptionOrigin implements Serializable {
    private static final long serialVersionUID = 5463122075630251207L;

    private final String code;
    private final String origin;

    public ExceptionOrigin(String code, String origin) {
        Objects.requireNonNull(code, "The code should not be null");
        Objects.requireNonNull(origin, "The origin should not be null");
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
