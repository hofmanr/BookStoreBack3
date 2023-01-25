package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.Date;

/**
 * @author Rinus Hofman
 */

@Entity
@Table(name = "ReturnShipments")
public class ReturnShipment extends BaseEntityVersion {
    private static final long serialVersionUID = 459336203474439l;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    protected Date registrationDate;

    @JoinColumn(name = "order_id", updatable = false)
    @OneToOne
    @NotNull
    private Order order;

    public ReturnShipment() {
    }

    public ReturnShipment(Date registrationDate, Order order) {
        this.registrationDate = registrationDate;
        this.order = order;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date shipmentDate) {
        this.registrationDate = shipmentDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "registrationDate=" + registrationDate +
                ", order=" + order +
                '}';
    }

}
