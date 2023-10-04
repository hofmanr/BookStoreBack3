package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MessageValidated extends MessageEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<ValidationResult> validationResults;

    public MessageValidated(Message message) {
        super(message);
        this.validationResults = Collections.emptyList();
    }
    public MessageValidated(Message message, List<ValidationResult> validationResults) {
        super(message);
        this.validationResults = validationResults;
    }

    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    public List<ValidationResult> getInvalidValidationResults() {
        return validationResults.stream().filter(ValidationResult::isInvalid).collect(Collectors.toList());
    }

}
