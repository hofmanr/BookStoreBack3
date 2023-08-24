package nl.rhofman.bookstore.ejb.validate.domain;

import java.io.Serializable;

public class ValidationResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean valid;
    private String validation;
    private String errorCode;
    private String errorMessage;
    private String errorLocation;

    public ValidationResult() {
    }

    public ValidationResult(boolean valid, String validation, String errorCode, String errorMessage) {
        this.valid = valid;
        this.validation = validation;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isInvalid() {
        return !valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorLocation() {
        return errorLocation;
    }

    public void setErrorLocation(String errorLocation) {
        this.errorLocation = errorLocation;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", validation='" + validation + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
