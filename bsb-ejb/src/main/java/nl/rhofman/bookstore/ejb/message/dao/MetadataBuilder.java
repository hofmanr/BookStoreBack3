package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.DomainObject;
import nl.rhofman.bookstore.persist.model.Metadata;

import java.time.LocalDateTime;

public class MetadataBuilder {
    private final DomainObject domainObject;
    private Long messageId;
    private String messageType;
    private String direction;
    private String orderNumber;

    public MetadataBuilder(DomainObject domainObject) {
        this.domainObject = domainObject;
    }

    public MetadataBuilder withMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public MetadataBuilder withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public MetadataBuilder withDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public MetadataBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    private void validate() {
        if (domainObject == null) {
            throw new NullPointerException("DomainObject is missing");
        }
        if (messageId == null) {
            throw new NullPointerException("Message has no ID");
        }
        if (messageType == null) {
            throw new NullPointerException("MessageType is missing");
        }
        if (direction == null) {
            throw new NullPointerException("Direction is missing");
        }
        if (!"IN".equals(direction) && !"OUT".equals(direction)) {
            throw new IllegalArgumentException("Direction must be 'IN' or 'OUT'");
        }
    }

    public Metadata build() {
        validate();
        Metadata metadata = MetadataMapper.instance()
                .mapToMessageMetadata(domainObject);
        metadata.setMessageId(messageId);
        metadata.setMessageType(messageType);
        metadata.setDirection(direction);
        metadata.setOrderNumber(orderNumber);
        if (metadata.getCreated() == null) {
            metadata.setCreated(LocalDateTime.now());
        }
        metadata.setProcessed(LocalDateTime.now());

        return metadata;
    }
}
