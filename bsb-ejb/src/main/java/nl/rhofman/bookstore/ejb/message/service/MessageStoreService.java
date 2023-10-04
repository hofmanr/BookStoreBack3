package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;

public abstract class MessageStoreService {

    @Inject
    private Event<MessageReceived> event;

    protected abstract void processMessageReceived(String xmlMessage);

    protected void fireEvent(MessageReceived message) {
        event.fire(message);
    }

}
