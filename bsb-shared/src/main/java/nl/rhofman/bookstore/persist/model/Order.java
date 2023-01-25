package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
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

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    protected Date orderDate;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    protected Date paymentDate;

    @JoinColumn(name = "customer_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne
    private Shipment shipment;

    public Order() {
    }

    public Order(Date orderDate, Customer customer) {
        this.orderDate = orderDate;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return orderDate.equals(order.orderDate) && customer.equals(order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderDate, customer);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", orderDate=" + orderDate +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
