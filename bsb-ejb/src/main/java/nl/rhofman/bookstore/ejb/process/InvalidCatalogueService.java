package nl.rhofman.bookstore.ejb.process;

import jakarta.enterprise.event.Observes;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.event.MessageValidated;
import nl.rhofman.bookstore.ejb.validate.domain.Invalid;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.util.List;

public class InvalidCatalogueService {

    public void processMessageValidatedEvent(@Observes @Invalid @DomainType(Catalogue.class) MessageValidated messageValidated) {
        System.out.println("InvalidCatalogueService");
        List<ValidationResult> invalidResults = messageValidated.getInvalidValidationResults();
        invalidResults.forEach(System.out::println);
    }

}
