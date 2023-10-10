package nl.rhofman.bookstore.ejb.message.domain;

import java.time.LocalDateTime;

public abstract class DomainObject {

    protected Long id; // ID of the Xml Message (=ID in the Messages table)
    protected Long parentId; // ID of the parent (incoming) Xml Message (=ID in the Messages table)
    protected String sender;
    protected String recipient;
    protected String messageID;
    protected String correlationID;
    protected LocalDateTime timestamp;

    protected DomainObject() {
    }

    public DomainObject(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
