package nl.rhofman.bookstore.persist.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Rinus Hofman
 * No Entity. Only a helper class.
 */

public class Customer implements Serializable {
    private static final long serialVersionUID = 233837455362534l;

    // ======================================
    // =             Attributes             =
    // ======================================

    private String customerNumber;

    private String name;

    private String address;

    private String postalCode;

    private String city;

    private String phone;

    private String email;

    public Customer() {
    }

    public Customer(String customerNumber, String name) {
        this.customerNumber = customerNumber;
        this.name = name;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
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
        return customerNumber.equals(customer.customerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "number='" + customerNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
