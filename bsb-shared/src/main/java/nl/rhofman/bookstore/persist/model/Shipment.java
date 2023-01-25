package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.util.Date;

/**
 * @author Rinus Hofman
 */

@Entity
@Table(name = "Shipments")
public class Shipment extends BaseEntityVersion {
    private static final long serialVersionUID = 739926203499442l;

    @Column(name = "shipment_date")
    @Temporal(TemporalType.DATE)
    protected Date shipmentDate;

    @JoinColumn(name = "order_id", updatable = false)
    @OneToOne
    @NotNull
    private Order order;

    public Shipment() {
    }

    public Shipment(Date shipmentDate, Order order) {
        this.shipmentDate = shipmentDate;
        this.order = order;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
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
                "shipmentDate=" + shipmentDate +
                ", order=" + order +
                '}';
    }
}
