package nl.rhofman.bookstore.ejb.message.domain;

import nl.rhofman.bookstore.ejb.message.dao.MetadataBuilder;
import nl.rhofman.bookstore.persist.model.Metadata;
import nl.rhofman.bookstore.persist.service.MessageService;

public class Message {

    private final Object domainObject;
    private final String xml;
    private final Header header;
    private final String direction;
    private Long messageID;

    private MessageService messageService;

    /**
     * Is protected so only the MessageBuilder can use the constructor
     * @param messageService
     * @param direction
     * @param domainObject
     * @param xml
     * @param header
     */
    protected Message(MessageService messageService, String direction, Object domainObject, String xml, Header header) {
        this.messageService = messageService;
        this.direction = direction;
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

    public Header getHeader() {
        return header;
    }

    public boolean isIncoming() {
        return "IN".equals(direction);
    }

    public boolean isOutgoing() {
        return "OUT".equals(direction);
    }

    public void save() {
        if (messageID != null) {
            return; // idempotent => the message is already stored
        }
        if (messageService == null) {
            new NullPointerException("MessageService is mandatory");
        }
        if (xml == null || header == null || direction == null) {
            new NullPointerException("XMl, Header and Direction must be present");
        }

        nl.rhofman.bookstore.persist.model.Message storedMessage = messageService.saveMessage(xml);
        // Store the message metadata
        Metadata metadata = new MetadataBuilder(header)
                .withMessageId(storedMessage.getId())
                .withMessageType(domainObject.getClass().getSimpleName())
                .withDirection(direction)
                .build();
        messageService.saveMetadata(metadata);

        messageID = storedMessage.getId();
    }
}
