package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Metadata;

import java.io.Serializable;

public class MessageValidated extends Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public MessageValidated(Long messageID, String messageType, Metadata metadata, Object domainObject) {
        super(messageID, messageType, metadata, domainObject);
    }
}
