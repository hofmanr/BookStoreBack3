package nl.rhofman.bookstore.ejb.message.domain;

import nl.rhofman.bookstore.ejb.message.dao.MetadataBuilder;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.persist.model.Metadata;

public class Message {

    private final Object domainObject;
    private final String xml;
    private final Header header;
    private final String direction;
    private Long messageID;
    private String messageType;

    /**
     * Is protected so only the MessageBuilder can use the constructor
     * @param direction
     * @param domainObject
     * @param xml
     * @param header
     */
    protected Message(String direction, Object domainObject, String xml, Header header) {
        this.direction = direction;
        this.domainObject = domainObject;
        this.xml = xml;
        this.header = header;

        if (domainObject != null) {
            this.messageType = domainObject.getClass().getSimpleName();
        } else if (xml != null) {
            this.messageType = XmlUtil.getRootElementName(xml);
        }

        if (this.xml == null || this.domainObject == null || this.direction == null) {
            new NullPointerException("XMl and DomainObject and Direction must be present");
        }
    }

    public <T> T getDomainObject() {
        return (T) domainObject;
    }

    public String getXml() {
        return xml;
    }

    public Metadata constructMetadata() {
        if (messageID == null) {
            new NullPointerException("XML Message is not stored");
        }
        if (header == null) {
            new NullPointerException("Header is not present");
        }
        return new MetadataBuilder(header)
                .withMessageId(messageID)
                .withMessageType(messageType)
                .withDirection(direction)
                .build();
    }

    public void storedWithID(Long messageID) {
        this.messageID = messageID;
    }

    public boolean isMessageStored() {
        return this.messageID != null;
    }

    public boolean isIncoming() {
        return "IN".equals(direction);
    }

    public boolean isOutgoing() {
        return "OUT".equals(direction);
    }

    public String sender() {
        return header == null ? null : header.getMessageSender();
    }

    public String recipient() {
        return header == null ? null : header.getMessageRecipient();
    }

    public String messageID() {
        return header == null ? null : header.getMessageID();
    }
}
