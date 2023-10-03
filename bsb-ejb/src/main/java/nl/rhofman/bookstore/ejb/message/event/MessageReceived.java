package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Header;

import java.io.Serializable;

public class MessageReceived extends MessageEvent implements Serializable {
    private static final long serialVersionUID = 1L;


    public MessageReceived(Long messageID, String messageType, Header header, Object domainObject) {
        super(messageID, messageType, header, domainObject);
    }

}
