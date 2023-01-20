package nl.rhofman.persist.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntityVersion extends BaseEntity {
    private static final long serialVersionUID = 898445026502862l;

    @Version
    @Column(name = "version")
    protected int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
