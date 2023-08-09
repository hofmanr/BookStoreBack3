package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.event.Message;

import java.lang.reflect.InvocationTargetException;

public class MessageBuilder {
    private Long messageID;
    private String messageType;
    private final Header header;
    private Object domainObject;

    public MessageBuilder(Header header) {
        this.header = header;
    }

    public MessageBuilder withMessageID(Long messageID) {
        this.messageID = messageID;
        return this;
    }

    public MessageBuilder withDomainObject(Object domainObject) {
        this.domainObject = domainObject;
        return this;
    }

    private void validate() {
        if (messageID == null) {
            throw new NullPointerException("MessageID is missing");
        }
        if (messageID <= 0L) {
            throw new IllegalArgumentException("MessageID cannot be negative");
        }
        if (domainObject != null) {
            messageType = domainObject.getClass().getSimpleName();
        }
        if (messageType == null) {
            throw new NullPointerException("Unknown MessageType");
        }
        if (header == null) {
            throw new NullPointerException("Header is missing");
        }
    }

    public <T extends Message> T build(Class<T> clazz) {
        validate();
        try {
            return clazz.getConstructor(Long.class, String.class, Header.class, Object.class)
                    .newInstance(messageID, messageType, header, domainObject);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
