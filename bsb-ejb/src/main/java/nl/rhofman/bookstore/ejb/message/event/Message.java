package nl.rhofman.bookstore.ejb.message.event;


import nl.rhofman.bookstore.ejb.message.domain.Metadata;

public abstract class Message {
    private final Long messageID;
    private final String messageType;
    private final Metadata metadata;
    private final Object domainObject;

    public Message(Long messageID, String messageType, Metadata metadata, Object domainObject) {
        this.messageID = messageID;
        this.messageType = messageType;
        this.metadata = metadata;
        this.domainObject = domainObject;
    }

    public String getMessageType() {
        return messageType;
    }

    public Metadata getMessageMetadata() {
        return metadata;
    }

    public Long getMessageID() {
        return messageID;
    }

    public Metadata getHeader() {
        return metadata;
    }

    public Object getDomainObject() {
        return domainObject;
    }
}
