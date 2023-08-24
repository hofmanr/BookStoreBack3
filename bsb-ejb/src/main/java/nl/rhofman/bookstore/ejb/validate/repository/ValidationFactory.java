package nl.rhofman.bookstore.ejb.validate.repository;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationAssembler;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationMessageLiteral;

import java.util.Collections;
import java.util.List;

public class ValidationFactory {

    @Inject
    @Any
    private Instance<ValidationAssembler<?>> validationAssemblers;

    public List<Validator> getValidators(Object object) {
        Instance<ValidationAssembler<?>> selectedAssemblers = null;
        ValidationAssembler<Object> assembler = null;
        try {
            selectedAssemblers = validationAssemblers.select(new ValidationMessageLiteral(object.getClass()));
            assembler = (selectedAssemblers == null || selectedAssemblers.isUnsatisfied() || selectedAssemblers.isAmbiguous() ?
                    null : (ValidationAssembler<Object>) selectedAssemblers.get());
            return (assembler == null ? Collections.emptyList() : assembler.map(object));
        } finally {
            if (assembler != null)
                selectedAssemblers.destroy(assembler);
        }
    }
}
