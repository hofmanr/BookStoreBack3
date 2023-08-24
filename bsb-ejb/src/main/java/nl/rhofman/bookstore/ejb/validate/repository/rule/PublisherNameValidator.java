package nl.rhofman.bookstore.ejb.validate.repository.rule;

import nl.rhofman.bookstore.ejb.validate.domain.InvalidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;

import java.util.Objects;

public class PublisherNameValidator extends Validator {
    private String lastName;

    public PublisherNameValidator(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public ValidationResult validate() {
        if (Objects.nonNull(lastName) && lastName.length() >= 3)
            return new ValidResult(validationName());
        return new InvalidResult(validationName(), errorCode, errorMessage);
    }

    @Override
    public String validationName() {
        return "BR002";
    }

}
