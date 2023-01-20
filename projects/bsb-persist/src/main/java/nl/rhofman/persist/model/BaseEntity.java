package nl.rhofman.persist.model;

import jakarta.persistence.*;

import java.io.Serializable;


@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 189872343479829l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
