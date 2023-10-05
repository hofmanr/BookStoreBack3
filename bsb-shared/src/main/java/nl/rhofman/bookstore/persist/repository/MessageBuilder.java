package nl.rhofman.bookstore.persist.repository;

import nl.rhofman.bookstore.persist.model.XmlMessage;

import java.time.LocalDateTime;

public class MessageBuilder {
    private String xml;

    public MessageBuilder(String messageXml) {
        this.xml = messageXml;
    }

    private void validate() {
        if (xml == null || xml.isBlank()) {
            throw new NullPointerException("Message can not be empty");
        }
    }

    public XmlMessage build() {
        validate();
        return new XmlMessage(LocalDateTime.now(), xml);
    }
}
