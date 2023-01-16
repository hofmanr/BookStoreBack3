package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.Date;

/**
 * @author Rinus Hofman
 */

@Entity
public class Author extends BaseEntityVersion {
    private static final long serialVersionUID = 648836203462534l;

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(length = 50, name = "first_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    protected String firstName;

    @Column(length = 50, name = "last_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    protected String lastName;

    @Column(length = 5000)
    @Size(max = 5000)
    protected String bio;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @Past
    protected Date dateOfBirth;

    @Transient
    protected Integer age;

    @Column(name = "preferred_language")
    @Convert(converter = LanguageConverter.class)
    private Language preferredLanguage;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
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
        if (firstName != null && !firstName.trim().isEmpty())
            result += ", firstName: " + firstName;
        if (lastName != null && !lastName.trim().isEmpty())
            result += ", lastName: " + lastName;
        if (bio != null && !bio.trim().isEmpty())
            result += ", bio: " + bio;
        if (dateOfBirth != null)
            result += ", dateOfBirth: " + dateOfBirth;
        if (age != null)
            result += ", age: " + age;
        if (preferredLanguage != null)
            result += ", preferredLanguage: " + preferredLanguage;
        return result;
    }
}
