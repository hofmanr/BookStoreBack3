package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Header;

import java.io.Serializable;

public class MessageValidated extends Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public MessageValidated(Long messageID, String messageType, Header header, Object domainObject) {
        super(messageID, messageType, header, domainObject);
    }
}
