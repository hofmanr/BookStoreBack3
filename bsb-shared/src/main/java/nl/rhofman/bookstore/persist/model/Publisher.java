package nl.rhofman.bookstore.persist.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Publishers")
public class Publisher extends BaseEntityVersion implements Serializable {
    private static final long serialVersionUID = 746746203462534l;

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(name = "name", length = 30, nullable = false)
    @NotNull
    @Size(max = 30)
    private String name;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher)) return false;
        if (!super.equals(o)) return false;
        Publisher publisher = (Publisher) o;
        return name.equals(publisher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

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
