package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;

public abstract class MessageReceivedService {

    @Inject
    private Event<MessageReceived> event;

    protected abstract void processMessageReceived(Message messgae);

    protected void fireEvent(MessageReceived message) {
        event.fire(message);
    }

}
