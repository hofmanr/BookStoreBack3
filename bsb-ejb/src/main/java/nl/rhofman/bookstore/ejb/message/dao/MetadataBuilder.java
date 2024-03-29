package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.persist.model.Metadata;

import java.time.LocalDateTime;

public class MetadataBuilder {
    private final Header header;
    private Long messageId;
    private Long parentId;
    private String messageType;
    private String direction;
    private String orderNumber;

    public MetadataBuilder(Header header) {
        this.header = header;
    }

    public MetadataBuilder withMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public MetadataBuilder withParentId(Long parentId) {
        this.parentId = parentId;
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
        if (header == null) {
            throw new NullPointerException("Header info is missing");
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
                .mapToMessageMetadata(header);
        metadata.setMessageId(messageId);
        metadata.setParentId(parentId);
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
