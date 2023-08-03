package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.Metadata;
import nl.rhofman.bookstore.ejb.message.event.Message;

import java.lang.reflect.InvocationTargetException;

public class MessageBuilder {
    private Long messageID;
    private String messageType;
    private Metadata metadata;
    private Object domainObject;

    public MessageBuilder withMessageID(Long messageID) {
        this.messageID = messageID;
        return this;
    }

    public MessageBuilder withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public MessageBuilder withMessageMetadata(Metadata metadata) {
        this.metadata = metadata;
        return this;
    }

    public MessageBuilder withDomainObject(Object domainObject) {
        this.domainObject = domainObject;
        return this;
    }

    private void validate() {
        if (messageID == null) {
            throw new NullPointerException("MessageID missing");
        }
        if (messageID <= 0L) {
            throw new IllegalArgumentException("MessageID cannot be negative");
        }
        if (messageType == null) {
            throw new NullPointerException("Unknown MessageType");
        }
        if (metadata == null) {
            throw new NullPointerException("Metadata missing");
        }
    }

    public <T extends Message> T build(Class<T> clazz) {
        validate();
        try {
            return clazz.getConstructor(Long.class, String.class, Metadata.class, Object.class)
                    .newInstance(messageID, messageType, metadata, domainObject);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
