package nl.rhofman.bookstore.ejb.message.domain;

import nl.rhofman.bookstore.ejb.message.dao.MetadataBuilder;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.persist.model.Metadata;

public class Message {

    private final DomainObject domainObject;
    private final String xml;
    private final String direction;
    private String messageType;

    /**
     * Is protected so only the MessageBuilder can use the constructor
     * @param direction
     * @param domainObject
     * @param xml
     */
    protected Message(String direction, DomainObject domainObject, String xml) {
        this.direction = direction;
        this.domainObject = domainObject;
        this.xml = xml;

        if (domainObject != null) {
            this.messageType = domainObject.getClass().getSimpleName();
        } else if (xml != null) {
            this.messageType = XmlUtil.getRootElementName(xml);
        }

        if (this.xml == null || this.domainObject == null || this.direction == null) {
            new NullPointerException("XMl and DomainObject and Direction must be present");
        }
    }

    public <T extends DomainObject> T getDomainObject() {
        return (T) domainObject;
    }

    public String getXml() {
        return xml;
    }

    public Metadata constructMetadata() {
        Long id = domainObject == null ? null : domainObject.id;
        if (id == null) {
            new NullPointerException("XML Message is not stored");
        }
        if (domainObject == null) {
            new NullPointerException("DomainObject is not present");
        }
        return new MetadataBuilder(domainObject)
                .withMessageId(id)
                .withMessageType(messageType)
                .withDirection(direction)
                .build();
    }

    public void storedWithID(Long messageID) {
        if (domainObject == null) {
            throw new IllegalStateException("Domain Object is not known");
        }
        if (domainObject.id != null) {
            throw new IllegalStateException("Message is already stored");
        }
        domainObject.id = messageID;
    }

    public boolean isMessageStored() {
        return domainObject == null ? false : domainObject.id != null;
    }

    public String sender() {
        return domainObject == null ? null : domainObject.getSender();
    }

    public String recipient() {
        return domainObject == null ? null : domainObject.getRecipient();
    }

    public String messageID() {
        return domainObject == null ? null : domainObject.getMessageID();
    }
}
