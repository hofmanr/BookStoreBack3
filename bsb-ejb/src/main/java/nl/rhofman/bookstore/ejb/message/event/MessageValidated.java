package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MessageValidated extends MessageEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<ValidationResult> validationResults;

    public MessageValidated(Long messageID, String messageType, Header header, Object domainObject) {
        super(messageID, messageType, header, domainObject);
        this.validationResults = Collections.emptyList();
    }

    public MessageValidated(Long messageID, String messageType, Header header, Object domainObject, List<ValidationResult> validationResults) {
        super(messageID, messageType, header, domainObject);
        this.validationResults = validationResults;
    }

    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    public List<ValidationResult> getInvalidValidationResults() {
        return validationResults.stream().filter(ValidationResult::isInvalid).collect(Collectors.toList());
    }

}
