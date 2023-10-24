package nl.rhofman.bookstore.ejb.message.domain;

import nl.rhofman.bookstore.ejb.message.dao.MetadataBuilder;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.persist.model.Metadata;

public class Message {

    private Long id; // ID of the Xml Message (=ID in the Messages table)
    private Long parentId; // ID of the parent (incoming) Xml Message (=ID in the Messages table)
    private Header header;
    private String messageType;
    private final String direction;
    private final DomainObject domainObject;
    private final String xml;

    /**
     * Is protected so only the MessageBuilder can use the constructor
     * @param direction
     * @param jmsDomainObject
     * @param xml
     * @param parentId
     */
    public Message(String direction, JmsDomainObject jmsDomainObject, String xml, Long parentId) {
        if ((xml != null && jmsDomainObject == null) || (xml == null && jmsDomainObject != null)) {
            new NullPointerException("XMl or DomainObject must be present");
        }

        this.header = jmsDomainObject.hasHeader();
        this.direction = direction;
        this.domainObject = jmsDomainObject.getDomainObject();
        this.xml = xml;
        this.parentId = parentId;

        if (domainObject != null) {
            this.messageType = domainObject.getClass().getSimpleName();
        } else if (xml != null) {
            this.messageType = XmlUtil.getRootElementName(xml);
        }
    }

    public <T extends DomainObject> T getDomainObject() {
        return (T) domainObject;
    }

    public String getXml() {
        return xml;
    }

    public Metadata constructMetadata() {
        if (id == null) {
            new NullPointerException("XML Message is not stored");
        }
        if (domainObject == null) {
            new NullPointerException("DomainObject is not present");
        }
        return new MetadataBuilder(header)
                .withMessageId(id)
                .withParentId(parentId)
                .withMessageType(messageType)
                .withDirection(direction)
                .build();
    }

    /**
     * Register the ID
     * @param id is the ID of the (XML)message in the database
     */
    public void storedWithID(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("Message is already stored");
        }
        this.id = id;
    }

    public boolean isMessageStored() {
        return id != null;
    }

    public Long id() {
        return id;
    }
    public Long parentID() {
        return parentId;
    }

    public Header header() {
        return header;
    }
}
