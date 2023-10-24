package nl.rhofman.bookstore.ejb.message.domain;

public class MessageStub extends Message {
    public MessageStub(String direction, JmsDomainObject jmsDomainObject, String xml) {
        super(direction, jmsDomainObject, xml, null);
    }
}
