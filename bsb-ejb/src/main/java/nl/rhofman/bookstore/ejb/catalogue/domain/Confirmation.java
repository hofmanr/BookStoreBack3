package nl.rhofman.bookstore.ejb.catalogue.domain;

import nl.rhofman.bookstore.ejb.message.domain.DomainObject;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.util.List;
import java.util.UUID;

public class Confirmation extends DomainObject {

    private boolean successful;
    private String errorMessage;

    public Confirmation(String sender, String recipient ) {
        this.sender = sender;
        this.recipient = recipient;
    }

    private Confirmation(ConfirmationBuilder builder) {
        super("BookStore", builder.receivedMessage.sender());
        Message receivedMessage = builder.receivedMessage;

        this.parentId = receivedMessage.getDomainObject().getId();
        this.messageID = UUID.randomUUID().toString();
        this.correlationID = receivedMessage.messageID();

        List<ValidationResult> validationResults = builder.validationResults;
        this.successful = validationResults.isEmpty();

        String errorMessage = validationResults.isEmpty() ? null : validationResults.get(0).getErrorMessage();
        this.errorMessage = errorMessage;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static class ConfirmationBuilder {

        private Message receivedMessage;
        private List<ValidationResult> validationResults;

        public ConfirmationBuilder(Message receivedMessage) {
            this.receivedMessage = receivedMessage;
        }

        public ConfirmationBuilder withValidationResults(List<ValidationResult> validationResults) {
            this.validationResults = validationResults;
            return this;
        }

        public Confirmation build() {
            return new Confirmation(this);
        }
    }

}
