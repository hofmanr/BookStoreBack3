package nl.rhofman.bookstore.web.book.domain;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import nl.rhofman.bookstore.web.book.converter.LanguageAdapter;

import java.time.LocalDate;

@JsonbPropertyOrder({"id", "firstName", "lastName", "dateOfBirth", "age", "bio", "preferredLanguage"})
public class AuthorDTO {
    private Long id;
    private String firstName = null;
    private String lastName = null;
    private String bio = null;
    private LocalDate dateOfBirth = null;
    private Integer age;
    @JsonbTypeAdapter(LanguageAdapter.class)
    private LanguageDTO preferredLanguage;

    public AuthorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LanguageDTO getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(LanguageDTO preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
