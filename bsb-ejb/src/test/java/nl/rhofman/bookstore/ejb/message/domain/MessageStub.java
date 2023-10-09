package nl.rhofman.bookstore.ejb.message.domain;

public class MessageStub extends Message {
    public MessageStub(String direction, BaseDto domainObject, String xml) {
        super(direction, domainObject, xml);
    }
}
