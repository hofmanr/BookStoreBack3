package nl.rhofman.bookstore.ejb.message.domain;

import java.time.LocalDateTime;

public class MessageMetadata {
    private String messageSender;
    private String messageRecipient;
    private String messageID;
    private String correlationID;
    private LocalDateTime timestamp;

    public MessageMetadata() {
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

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageMetadata{" +
                "messageSender='" + messageSender + '\'' +
                ", messageRecipient='" + messageRecipient + '\'' +
                ", messageID='" + messageID + '\'' +
                ", correlationID='" + correlationID + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
