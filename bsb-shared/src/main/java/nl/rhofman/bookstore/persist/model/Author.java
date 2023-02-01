package nl.rhofman.bookstore.persist.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.*;

/**
 * @author Rinus Hofman
 */

@Entity
@Table(name = "Authors")
public class Author extends BaseEntityVersion {
    private static final long serialVersionUID = 648836203462534l;

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(length = 50, name = "first_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(length = 50, name = "last_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(length = 5000, name = "bio")
    @Size(max = 5000)
    private String bio;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @Past
    private Date dateOfBirth;

    @Transient
    private Integer age;

    @Column(name = "preferred_language")
    @Convert(converter = LanguageConverter.class)
    private Language preferredLanguage;

    @JsonbTransient
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

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
    // =         Lifecycle methods          =
    // ======================================

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateAge() {
        if (dateOfBirth == null) {
            age = null;
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTime(dateOfBirth);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
// ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) && lastName.equals(author.lastName) && Objects.equals(dateOfBirth, author.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, dateOfBirth);
    }

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
