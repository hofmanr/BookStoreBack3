package nl.rhofman.bookstore.ejb.message.event;


import nl.rhofman.bookstore.ejb.message.domain.MessageMetadata;

public abstract class Message {
    private final Long messageID;
    private final MessageMetadata messageMetadata;
    private final Object domainObject;

    public Message(Long messageID, MessageMetadata messageMetadata, Object domainObject) {
        this.messageID = messageID;
        this.messageMetadata = messageMetadata;
        this.domainObject = domainObject;
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
