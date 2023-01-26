package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.Date;
import java.util.Objects;

/**
 * @author Rinus Hofman
 */

@Entity
@Table(name = "Orders")
public class Order extends BaseEntityVersion {
    private static final long serialVersionUID = 459336203474439l;

    @Column(name = "order_number", length = 18, nullable = false)
    @NotNull
    @Size(min = 1, max = 18)
    private String orderNumber;

    @Column(name = "customer_number", length = 20, nullable = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String customerNumber;

    @Column(name = "customer_name", length = 40, nullable = false)
    @NotNull
    @Size(min = 1, max = 40)
    private String customerName;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    protected Date orderDate;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    protected Date paymentDate;

    public Order() {
    }

    public Order(Date orderDate, Customer customer) {
        this.orderDate = orderDate;
        this.customerNumber = customer.getCustomerNumber();
        this.customerName = customer.getName();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return orderNumber.equals(order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderNumber);
    }

    @Override
    public String toString() {
        return "Order{" +
                "order=" + orderNumber +
                ", customer=" + customerName +
                ", orderDate=" + orderDate +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
