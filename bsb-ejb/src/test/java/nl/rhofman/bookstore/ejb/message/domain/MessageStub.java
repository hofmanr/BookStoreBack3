package nl.rhofman.bookstore.ejb.message.domain;

public class MessageStub extends Message {
    public MessageStub(String direction, Object domainObject, String xml, Header header) {
        super(direction, domainObject, xml, header);
    }
}
