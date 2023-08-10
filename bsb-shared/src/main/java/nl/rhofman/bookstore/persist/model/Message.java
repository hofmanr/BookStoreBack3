package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import nl.rhofman.persist.model.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class Message extends BaseEntity {
    private static final long serialVersionUID = 34965320836372534l;

    @Column(name = "created", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime created;

    @Column(name = "message", length = 32672, nullable = false)
    private String messageXml;

    // ======================================
    // =            Constructors            =
    // ======================================


    public Message() {
    }

    public Message(LocalDateTime created, String messageXml) {
        this.created = created;
        this.messageXml = messageXml;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================


    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getMessageXml() {
        return messageXml;
    }

    public void setMessageXml(String messageXml) {
        this.messageXml = messageXml;
    }

}