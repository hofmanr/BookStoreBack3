package nl.rhofman.bookstore.ejb.message.event;


import nl.rhofman.bookstore.ejb.message.domain.MessageMetadata;

public abstract class Message {
    private final Long messageID;
    private final String messageType;
    private final MessageMetadata messageMetadata;
    private final Object domainObject;

    public Message(Long messageID, String messageType, MessageMetadata messageMetadata, Object domainObject) {
        this.messageID = messageID;
        this.messageType = messageType;
        this.messageMetadata = messageMetadata;
        this.domainObject = domainObject;
    }

    public String getMessageType() {
        return messageType;
    }

    public MessageMetadata getMessageMetadata() {
        return messageMetadata;
    }

    public Long getMessageID() {
        return messageID;
    }

    public MessageMetadata getHeader() {
        return messageMetadata;
    }

    public Object getDomainObject() {
        return domainObject;
    }
}
