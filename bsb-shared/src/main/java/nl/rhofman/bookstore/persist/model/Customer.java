package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.Objects;

/**
 * @author Rinus Hofman
 */

@Entity
@Table(name = "Customers")
public class Customer extends BaseEntityVersion {
    private static final long serialVersionUID = 233837455362534l;

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(length = 40, name = "name", nullable = false)
    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @Column(length = 50, name = "address")
    @Size(max = 50)
    private String address;

    @Column(length = 10, name = "postal_code")
    @Size(max = 10)
    private String postalCode;

    @Column(length = 40, name = "city")
    @Size(max = 40)
    private String city;

    @Column(length = 12, name = "phone")
    @Size(max = 12)
    private String phone;

    @Column(length = 40, name = "email")
    @Size(max = 40)
    private String email;

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name) && Objects.equals(postalCode, customer.postalCode) && Objects.equals(city, customer.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, postalCode, city);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
