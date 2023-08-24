package nl.rhofman.bookstore.ejb.validate.domain;

public class ValidResult extends ValidationResult {
    public ValidResult(String validation) {
        super(true, validation, "", "");
    }
}
