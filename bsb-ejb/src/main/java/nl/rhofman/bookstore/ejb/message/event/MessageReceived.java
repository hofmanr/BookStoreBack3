package nl.rhofman.bookstore.ejb.message.event;

import nl.rhofman.bookstore.ejb.message.domain.Message;

import java.io.Serializable;

public class MessageReceived extends MessageEvent implements Serializable {
    private static final long serialVersionUID = 1L;


    public MessageReceived(Message message) {
        super(message);
    }

}
