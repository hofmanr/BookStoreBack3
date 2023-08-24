package nl.rhofman.bookstore.ejb.validate.domain;

public class InvalidResult extends ValidationResult {
    public InvalidResult(String validation, String errorCode, String errorMessage) {
        super(false, validation, errorCode, errorMessage);
    }
}
