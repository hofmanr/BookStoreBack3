package nl.rhofman.bookstore.ejb.validate.repository.rule;


import nl.rhofman.bookstore.ejb.validate.domain.InvalidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;

import java.util.Objects;

/**
 * Initials and firstname are mandatory
 */
public class AuthorNameValidator extends Validator {
    private String firstName;
    private String initials;

    public AuthorNameValidator(String firstName, String initials) {
        this.firstName = firstName;
        this.initials = initials;
    }

    @Override
    public ValidationResult validate() {
        if (Objects.nonNull(firstName) || Objects.nonNull(initials))
            return new ValidResult(validationName());
        return new InvalidResult(validationName(), errorCode, errorMessage);
    }

    @Override
    public String validationName() {
        return "BR001";
    }
}
