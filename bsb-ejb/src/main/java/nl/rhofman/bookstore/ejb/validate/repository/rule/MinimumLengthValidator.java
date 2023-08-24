package nl.rhofman.bookstore.ejb.validate.repository.rule;


import nl.rhofman.bookstore.ejb.validate.domain.InvalidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;

import java.util.Objects;

public class MinimumLengthValidator extends Validator {
    private String subject;
    private String value;
    private Integer length;

    public MinimumLengthValidator(String subject, String value, Integer length) {
        this.subject = subject;
        this.value = value;
        this.length = length;
    }

    @Override
    public ValidationResult validate() {
        if (Objects.nonNull(value) && value.length() >= length)
            return new ValidResult(validationName());
        return new InvalidResult(validationName(), errorCode, errorMessage(subject, length.toString()));
    }

    @Override
    public String validationName() {
        return "BR005";
    }

}
