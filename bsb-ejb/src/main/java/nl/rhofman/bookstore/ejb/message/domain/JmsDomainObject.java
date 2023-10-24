package nl.rhofman.bookstore.ejb.message.domain;

import java.time.LocalDateTime;

public abstract class JmsDomainObject<T extends DomainObject> {
    private String sender;
    private String recipient;
    private String messageID;
    private String correlationID;
    private LocalDateTime timestamp;
    private T domainObject;

    public JmsDomainObject() {
    }

    public JmsDomainObject(T domainObject) {
        this.domainObject = domainObject;
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

    public T getDomainObject() {
        return domainObject;
    }

    public void setDomainObject(T domainObject) {
        this.domainObject = domainObject;
    }

    public void withHeader(Header header) {
        this.sender = header.getSender();
        this.recipient = header.getRecipient();
        this.messageID = header.getMessageID();
        this.correlationID = header.getCorrelationID();
        this.timestamp = header.getTimestamp();
    }

    public Header hasHeader() {
        Header header = new Header();
        header.setSender(sender);
        header.setRecipient(recipient);
        header.setMessageID(messageID);
        header.setCorrelationID(correlationID);
        header.setTimestamp(timestamp);
        return header;
    }
}
