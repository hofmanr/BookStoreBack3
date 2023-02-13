package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.MessageMetadata;

import java.io.Serializable;

public class MessageReceived extends Message implements Serializable {
    private static final long serialVersionUID = 1L;


    public MessageReceived(Long messageID, MessageMetadata metadata, Object domainObject) {
        super(messageID, metadata, domainObject);
    }

}
