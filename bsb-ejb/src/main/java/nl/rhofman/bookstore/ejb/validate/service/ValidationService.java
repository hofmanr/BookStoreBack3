package nl.rhofman.bookstore.ejb.validate.service;

import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.validate.repository.ValidationFactory;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationService {

    @Inject
    private ValidationFactory validationFactory;

    public List<ValidationResult> validate(Object domainObject) {
        List<Validator> validators = validationFactory.getValidators(domainObject);
        if (validators != null && !validators.isEmpty())
            return validators.stream().map(Validator::validate).collect(Collectors.toList());
        return Collections.emptyList();
    }
}
