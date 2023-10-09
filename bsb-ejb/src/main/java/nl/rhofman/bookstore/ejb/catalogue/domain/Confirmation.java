package nl.rhofman.bookstore.ejb.catalogue.domain;

import nl.rhofman.bookstore.ejb.message.domain.BaseDto;

public class Confirmation extends BaseDto {

    private boolean successful;
    private String errorMessage;

    public Confirmation(String sender, String recipient) {
        super(sender, recipient);
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
}
