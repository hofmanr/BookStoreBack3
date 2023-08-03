package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "message_metadata")
public class MessageMetadata extends BaseEntityVersion {
    private static final long serialVersionUID = 64966350836372843l;

    @Basic(optional = false)
    @Column(name = "identification", length = 44, nullable = false)
    @NotNull
    @Size(min = 1, max = 44)
    private String identification;

    @Column(name = "correlation_id", length = 44, nullable = false)
    @Size(min = 1, max = 44)
    private String correlationId;

    @Basic(optional = false)
    @Column(name = "message_type", length = 12, nullable = false)
    @NotNull
    @Size(min = 1, max = 12)
    private String messageType;

    /// values: IN, OUT
    @Basic(optional = false)
    @Column(name = "direction", length = 3, nullable = false)
    @NotNull
    @Size(min = 1, max = 3)
    private String direction;

    @Basic(optional = false)
    @Column(name = "message_sender", length = 16, nullable = false)
    @NotNull
    @Size(min = 1, max = 16)
    private String messageSender;

    @Basic(optional = false)
    @Column(name = "message_recipient", length = 16, nullable = false)
    @NotNull
    @Size(min = 1, max = 16)
    private String messageRecipient;

    // Date of preparation in message (header)
    @Column(name = "created", columnDefinition = "TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    @Column(name = "processed", columnDefinition = "TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime processed;

    @Basic(optional = false)
    @Column(name = "message_id", unique = true, nullable = false)
    @NotNull
    private Long messageId;

    @Column(name = "parent_message_id")
    private Long parentId;

    @OneToOne(optional = false)
    @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Message message;

    @OneToOne
    @JoinColumn(name = "parent_message_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Message parentMessage;

    public MessageMetadata() {
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageRecipient() {
        return messageRecipient;
    }

    public void setMessageRecipient(String messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getProcessed() {
        return processed;
    }

    public void setProcessed(LocalDateTime processed) {
        this.processed = processed;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageMetadata)) return false;
        if (!super.equals(o)) return false;
        MessageMetadata that = (MessageMetadata) o;
        return Objects.equals(identification, that.identification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), identification);
    }

    @Override
    public String toString() {
        return "MessageMetadata{" +
                "identification='" + identification + '\'' +
                ", messageType='" + messageType + '\'' +
                ", direction='" + direction + '\'' +
                ", messageSender='" + messageSender + '\'' +
                ", messageRecipient='" + messageRecipient + '\'' +
                ", created=" + created +
                '}';
    }
}