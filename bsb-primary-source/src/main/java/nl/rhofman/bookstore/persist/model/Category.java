package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.io.Serializable;

@Entity
public class Category extends BaseEntityVersion implements Serializable {
    private static final long serialVersionUID = 835201203463782l;

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(length = 100)
    @NotNull
    @Size(max = 100)
    private String name;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", version: " + version;
        if (name != null && !name.trim().isEmpty())
            result += ", name: " + name;
        return result;
    }
}
