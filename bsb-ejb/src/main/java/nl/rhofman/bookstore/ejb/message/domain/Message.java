package nl.rhofman.bookstore.ejb.message.domain;

public class Message {

    private Object domainObject;
    private String xml;
    private Header header;

    public Message(Object domainObject, String xml, Header header) {
        this.domainObject = domainObject;
        this.xml = xml;
        this.header = header;
    }

    public <T> T getDomainObject() {
        return (T) domainObject;
    }

    public String getXml() {
        return xml;
    }
}
