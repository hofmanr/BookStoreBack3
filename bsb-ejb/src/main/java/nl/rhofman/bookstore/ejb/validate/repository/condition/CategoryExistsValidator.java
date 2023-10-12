package nl.rhofman.bookstore.ejb.validate.repository.condition;

import nl.rhofman.bookstore.ejb.validate.domain.InvalidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidResult;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;
import nl.rhofman.bookstore.ejb.validate.repository.Validator;
import nl.rhofman.bookstore.persist.service.CategoryService;

public class CategoryExistsValidator extends Validator {
    private String category;
    private CategoryService service;

    public CategoryExistsValidator(String category, CategoryService service) {
        this.category = category;
        this.service = service;
    }

    @Override
    public ValidationResult validate() {
        if (category == null) {
            new ValidResult(validationName());
        }
        if (service.findByName(category) != null) {
            return new ValidResult(validationName());
        }
        return new InvalidResult(validationName(), errorCode, errorMessage(category));
    }

    @Override
    public String validationName() {
        return "C001";
    }

}
