package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@DiscriminatorValue("P")  // type Payment
@NamedQueries({
        @NamedQuery(name = Timer.FIND_SHIPMENT, query = "SELECT t FROM PaymentTimer t WHERE t.shipment = :shipment")
})
public class PaymentTimer extends Timer implements Serializable {

    @JoinColumn(name = "shipment_id", updatable = false)
    @OneToOne
    @NotNull
    private Shipment shipment;

    @Column(name = "reminder", nullable = false, columnDefinition = "smallint")
    @NotNull
    private Integer reminder;

    public PaymentTimer() {
    }

    public PaymentTimer(Shipment shipment, @Future Date timerDate, Boolean reminder) {
        this.shipment = shipment;
        this.timerDate = timerDate;
        this.reminder = reminder ? 1 : 0;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Integer getReminder() {
        return reminder;
    }

    public void setReminder(Integer reminder) {
        this.reminder = reminder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTimer)) return false;
        if (!super.equals(o)) return false;
        PaymentTimer that = (PaymentTimer) o;
        return shipment.equals(that.shipment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shipment);
    }

    @Override
    public String toString() {
        return "PaymentTimer{" +
                "shipment=" + shipment +
                ", reminder=" + reminder +
                ", timerDate=" + timerDate +
                '}';
    }
}
