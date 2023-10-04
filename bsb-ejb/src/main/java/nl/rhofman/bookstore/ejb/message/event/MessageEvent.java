package nl.rhofman.bookstore.ejb.message.event;


import nl.rhofman.bookstore.ejb.message.domain.Message;

public abstract class MessageEvent {
    private final Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
