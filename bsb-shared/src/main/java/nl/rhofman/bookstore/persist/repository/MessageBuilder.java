package nl.rhofman.bookstore.persist.repository;

import nl.rhofman.bookstore.persist.model.Message;

import java.time.LocalDateTime;

public class MessageBuilder {
    private String messageXml;

    public MessageBuilder(String messageXml) {
        this.messageXml = messageXml;
    }

    private void validate() {
        if (messageXml == null || messageXml.isBlank()) {
            throw new NullPointerException("Message can not be empty");
        }
    }

    public Message build() {
        validate();
        return new Message(LocalDateTime.now(), messageXml);
    }
}
