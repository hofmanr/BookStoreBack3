package nl.rhofman.bookstore.ejb.message.event;


import nl.rhofman.bookstore.ejb.message.domain.Header;

public abstract class MessageEvent {
    private final Long messageID;
    private final String messageType;
    private final Header header;
    private final Object domainObject;

    public MessageEvent(Long messageID, String messageType, Header header, Object domainObject) {
        this.messageID = messageID;
        this.messageType = messageType;
        this.header = header;
        this.domainObject = domainObject;
    }

    public String getMessageType() {
        return messageType;
    }

    public Long getMessageID() {
        return messageID;
    }

    public Header getHeader() {
        return header;
    }

    public Object getDomainObject() {
        return domainObject;
    }
}
