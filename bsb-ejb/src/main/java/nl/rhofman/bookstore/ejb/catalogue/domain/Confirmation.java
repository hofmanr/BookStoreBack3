package nl.rhofman.bookstore.ejb.catalogue.domain;

public class Confirmation {

    private final String sender;
    private final String recipient;
    private String messageID;
    private String correlationID;
    private boolean successful;
    private String errorMessage;

    public Confirmation(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
