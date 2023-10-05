package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import nl.rhofman.persist.model.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class XmlMessage extends BaseEntity {
    private static final long serialVersionUID = 34965320836372534l;

    @Column(name = "created", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime created;

    @Column(name = "message", length = 32672, nullable = false)
    private String xml;

    // ======================================
    // =            Constructors            =
    // ======================================


    public XmlMessage() {
    }

    public XmlMessage(LocalDateTime created, String xml) {
        this.created = created;
        this.xml = xml;
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

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

}