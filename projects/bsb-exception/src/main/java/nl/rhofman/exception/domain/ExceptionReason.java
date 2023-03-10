package nl.rhofman.exception.domain;

import java.io.Serializable;
import java.util.Objects;

public class ExceptionReason implements Serializable {
    private static final long serialVersionUID = 7362522041950730267L;
    private final String code;
    private final String reason;

    public ExceptionReason(String code, String reason) {
        Objects.requireNonNull(code, "The code should not be null");
        Objects.requireNonNull(reason, "The reason should not be null");
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionReason that = (ExceptionReason) o;
        return code.equals(that.code) && reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, reason);
    }
}
