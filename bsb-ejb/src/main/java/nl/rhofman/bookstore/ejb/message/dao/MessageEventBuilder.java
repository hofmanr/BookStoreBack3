package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageEvent;

import java.lang.reflect.InvocationTargetException;

public class MessageEventBuilder {
    private Message message;

    public MessageEventBuilder(Message message) {
        this.message = message;
    }

    private void validate() {
        if (message == null) {
            throw new NullPointerException("Message is missing");
        }
    }

    public <T extends MessageEvent> T build(Class<T> clazz) {
        validate();
        try {
            return clazz.getConstructor(Message.class).newInstance(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
