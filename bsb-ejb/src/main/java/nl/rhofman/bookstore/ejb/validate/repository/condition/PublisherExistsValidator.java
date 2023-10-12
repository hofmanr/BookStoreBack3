package nl.rhofman.bookstore.ejb.validate.repository.condition;

import nl.rhofman.bookstore.ejb.validate.domain.InvalidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;
import nl.rhofman.bookstore.persist.service.PublisherService;

public class PublisherExistsValidator extends Validator {
    private String publisher;
    private PublisherService service;

    public PublisherExistsValidator(String publisher, PublisherService service) {
        this.publisher = publisher;
        this.service = service;
    }

    @Override
    public ValidationResult validate() {
        if (publisher == null) {
            new ValidResult(validationName());
        }
        if (service.findByName(publisher) != null) {
            return new ValidResult(validationName());
        }
        return new InvalidResult(validationName(), errorCode, errorMessage(publisher));
    }

    @Override
    public String validationName() {
        return "C002";
    }

}
