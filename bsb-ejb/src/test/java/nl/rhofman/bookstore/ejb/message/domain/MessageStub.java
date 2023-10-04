package nl.rhofman.bookstore.ejb.message.domain;

import nl.rhofman.bookstore.persist.service.MessageService;

public class MessageStub extends Message {
    public MessageStub(MessageService messageService, String direction, Object domainObject, String xml, Header header) {
        super(messageService, direction, domainObject, xml, header);
    }
}
