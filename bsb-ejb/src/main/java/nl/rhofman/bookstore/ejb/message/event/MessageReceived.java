package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Metadata;

import java.io.Serializable;

public class MessageReceived extends Message implements Serializable {
    private static final long serialVersionUID = 1L;


    public MessageReceived(Long messageID, String messageType, Metadata metadata, Object domainObject) {
        super(messageID, messageType, metadata, domainObject);
    }

}
